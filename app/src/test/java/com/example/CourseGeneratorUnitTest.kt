package com.example

import com.example.data.model.GradeResult
import com.example.data.model.ScopeTier
import com.example.data.remote.OfflineCourseTemplates
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CourseGeneratorUnitTest {

    @Test
    fun testGradingEngineAtoFScale() {
        val gradeA = GradeResult.calculate(95, 100)
        assertEquals("A", gradeA.gradeLetter)
        assertEquals(95, gradeA.scorePercentage)

        val gradeB = GradeResult.calculate(85, 100)
        assertEquals("B", gradeB.gradeLetter)
        assertEquals(85, gradeB.scorePercentage)

        val gradeC = GradeResult.calculate(72, 100)
        assertEquals("C", gradeC.gradeLetter)
        assertEquals(72, gradeC.scorePercentage)

        val gradeD = GradeResult.calculate(60, 100)
        assertEquals("D", gradeD.gradeLetter)
        assertEquals(60, gradeD.scorePercentage)

        val gradeF = GradeResult.calculate(45, 100)
        assertEquals("F", gradeF.gradeLetter)
        assertEquals(45, gradeF.scorePercentage)
    }

    @Test
    fun testScopeTiersConfiguration() {
        assertEquals(3, ScopeTier.SHORT.targetSections)
        assertEquals(6, ScopeTier.MEDIUM.targetSections)
        assertEquals(10, ScopeTier.LONG.targetSections)
    }

    @Test
    fun testProficiencyLevelAndDurationConfigurations() {
        // Proficiency levels
        assertEquals("Beginner", com.example.data.model.ProficiencyLevel.BEGINNER.title)
        assertEquals("Intermediate", com.example.data.model.ProficiencyLevel.INTERMEDIATE.title)
        assertEquals("Advanced", com.example.data.model.ProficiencyLevel.ADVANCED.title)
        assertEquals("Mastery / Expert", com.example.data.model.ProficiencyLevel.MASTER.title)

        // Course durations
        assertEquals(3, com.example.data.model.CourseDuration.CRASH_COURSE.targetSections)
        assertEquals(5, com.example.data.model.CourseDuration.STANDARD.targetSections)
        assertEquals(8, com.example.data.model.CourseDuration.DEEP_DIVE.targetSections)
        assertEquals(10, com.example.data.model.CourseDuration.MASTERCLASS.targetSections)

        // Generate with parameters
        val advMasterclass = OfflineCourseTemplates.generateDynamicOfflineCourse(
            topic = "Neural Quantum Optics",
            proficiencyLevel = com.example.data.model.ProficiencyLevel.ADVANCED,
            duration = com.example.data.model.CourseDuration.MASTERCLASS
        )
        assertEquals(com.example.data.model.ProficiencyLevel.ADVANCED, advMasterclass.proficiencyLevel)
        assertEquals(com.example.data.model.CourseDuration.MASTERCLASS, advMasterclass.duration)
        assertEquals(10, advMasterclass.sections.size)
        assertTrue(advMasterclass.flashcards.isNotEmpty())
        assertTrue(advMasterclass.examQuestions.isNotEmpty())
        assertTrue(advMasterclass.description.contains("Advanced"))
    }

    @Test
    fun testDynamicCourseGenerationStructure() {
        val shortCourse = OfflineCourseTemplates.generateDynamicOfflineCourse("Quantum Teleportation", ScopeTier.SHORT)
        assertEquals(3, shortCourse.sections.size)
        assertTrue(shortCourse.flashcards.isNotEmpty())
        assertTrue(shortCourse.examQuestions.isNotEmpty())

        // Check active recall checkpoints
        shortCourse.sections.forEach { section ->
            assertEquals(3, section.takeaways.size)
            assertTrue(section.checkpoint.options.size >= 2)
            assertTrue(section.checkpoint.explanation.isNotBlank())
        }

        val mediumCourse = OfflineCourseTemplates.generateDynamicOfflineCourse("Compiler Optimization", ScopeTier.MEDIUM)
        assertEquals(6, mediumCourse.sections.size)

        val longCourse = OfflineCourseTemplates.generateDynamicOfflineCourse("Distributed Systems", ScopeTier.LONG)
        assertEquals(9, longCourse.sections.size)
    }

    @Test
    fun testCourseProgressPhasesAndMetrics() {
        val course = OfflineCourseTemplates.getCuratedStarterCourses().first()
        val sections = course.sections
        val flashcards = course.flashcards
        val attempts = emptyList<com.example.data.model.TestAttempt>()

        // 1. Not started
        val unstartedSections = sections.map { it.copy(isCompleted = false) }
        val stateNotStarted = com.example.ui.components.CourseProgressState.fromCourseData(
            course = course,
            sections = unstartedSections,
            flashcards = flashcards,
            attempts = attempts
        )
        assertEquals(com.example.ui.components.CoursePhase.NOT_STARTED, stateNotStarted.phase)
        assertEquals(0, stateNotStarted.completedSections)
        assertEquals(0f, stateNotStarted.sectionProgress, 0.01f)
        assertEquals(0, stateNotStarted.nextModuleIndex)

        // 2. In progress
        val inProgressSections = unstartedSections.mapIndexed { idx, s ->
            if (idx == 0) s.copy(isCompleted = true) else s
        }
        val stateInProgress = com.example.ui.components.CourseProgressState.fromCourseData(
            course = course,
            sections = inProgressSections,
            flashcards = flashcards,
            attempts = attempts
        )
        assertEquals(com.example.ui.components.CoursePhase.IN_PROGRESS, stateInProgress.phase)
        assertEquals(1, stateInProgress.completedSections)
        assertEquals(1, stateInProgress.nextModuleIndex)
        assertTrue(stateInProgress.nextActionLabel.contains("Resume Module 2"))

        // 3. Exam ready (all sections done, 0 attempts)
        val allCompletedSections = unstartedSections.map { it.copy(isCompleted = true) }
        val stateExamReady = com.example.ui.components.CourseProgressState.fromCourseData(
            course = course,
            sections = allCompletedSections,
            flashcards = flashcards,
            attempts = attempts
        )
        assertEquals(com.example.ui.components.CoursePhase.EXAM_READY, stateExamReady.phase)
        assertEquals(1.0f, stateExamReady.sectionProgress, 0.01f)
        assertEquals("Take Final Exam", stateExamReady.nextActionLabel)

        // 4. Mastered (exam passed with >= 70%)
        val attemptMastered = listOf(
            com.example.data.model.TestAttempt(
                id = "att1",
                courseId = course.id,
                attemptNumber = 1,
                scorePercentage = 90,
                gradeLetter = "A",
                totalQuestions = 10,
                correctCount = 9,
                questionFeedbacks = emptyList()
            )
        )
        val stateMastered = com.example.ui.components.CourseProgressState.fromCourseData(
            course = course.copy(bestScore = 90),
            sections = allCompletedSections,
            flashcards = flashcards.map { it.copy(isMastered = true) },
            attempts = attemptMastered
        )
        assertEquals(com.example.ui.components.CoursePhase.MASTERED, stateMastered.phase)
        assertTrue(stateMastered.overallProgress >= 0.9f)
    }

    @Test
    fun testSkilledTradesCurriculaAndFreeCertifications() {
        val millwright = com.example.data.remote.SkilledTradesCourseTemplates.createMillwrightCourse()
        assertEquals(com.example.data.model.EducationalCategory.SKILLED_TRADES, millwright.category)
        assertEquals("OSHA 10 General", millwright.certification?.shortName)
        assertEquals(6, millwright.sections.size)
        assertTrue(millwright.flashcards.isNotEmpty())
        assertTrue(millwright.examQuestions.isNotEmpty())

        val hvac = com.example.data.remote.SkilledTradesCourseTemplates.createHvacCourse()
        assertEquals(com.example.data.model.EducationalCategory.SKILLED_TRADES, hvac.category)
        assertEquals("EPA 608 Universal", hvac.certification?.shortName)

        val electrician = com.example.data.remote.SkilledTradesCourseTemplates.createElectricianCourse()
        assertEquals(com.example.data.model.EducationalCategory.SKILLED_TRADES, electrician.category)
        assertEquals("Schneider Electric Cert", electrician.certification?.shortName)

        val carpentry = com.example.data.remote.SkilledTradesCourseTemplates.createCarpentryCourse()
        assertEquals(com.example.data.model.EducationalCategory.SKILLED_TRADES, carpentry.category)
        assertEquals("OSHA 10 Construction", carpentry.certification?.shortName)

        val welding = com.example.data.remote.SkilledTradesCourseTemplates.createWeldingCourse()
        assertEquals(com.example.data.model.EducationalCategory.SKILLED_TRADES, welding.category)

        val firstAid = com.example.data.remote.SkilledTradesCourseTemplates.createFirstAidCourse()
        assertEquals(com.example.data.model.EducationalCategory.HEALTHCARE_SAFETY, firstAid.category)
        assertEquals("CPR & First Aid Life Support", firstAid.certification?.shortName)

        // Verify all starter courses have appropriate categories
        val starters = OfflineCourseTemplates.getCuratedStarterCourses()
        assertTrue(starters.size >= 9)
        assertTrue(starters.any { it.category == com.example.data.model.EducationalCategory.SKILLED_TRADES })
        assertTrue(starters.any { it.category == com.example.data.model.EducationalCategory.COMPUTER_SCIENCE })
        assertTrue(starters.any { it.category == com.example.data.model.EducationalCategory.HEALTHCARE_SAFETY })
    }

    @Test
    fun testAcademicPremadeCoursesContent() {
        val academicCourses = com.example.data.remote.AcademicCourseTemplates.getAllAcademicStarterCourses()
        assertTrue("Expected at least 8 academic starter courses", academicCourses.size >= 8)

        // Math
        val calc = academicCourses.find { it.title.contains("Calculus") }
        org.junit.Assert.assertNotNull("Calculus course should exist", calc)
        assertEquals(com.example.data.model.EducationalCategory.MATHEMATICS, calc?.category)
        assertTrue(calc!!.sections.isNotEmpty())
        assertTrue(calc.flashcards.isNotEmpty())
        assertTrue(calc.examQuestions.isNotEmpty())

        val linearAlg = academicCourses.find { it.title.contains("Linear Algebra") }
        org.junit.Assert.assertNotNull("Linear Algebra course should exist", linearAlg)
        assertEquals(com.example.data.model.EducationalCategory.MATHEMATICS, linearAlg?.category)

        // Science
        val astro = academicCourses.find { it.title.contains("Astrophysics") }
        org.junit.Assert.assertNotNull("Astrophysics course should exist", astro)
        assertEquals(com.example.data.model.EducationalCategory.NATURAL_SCIENCES, astro?.category)

        val genetics = academicCourses.find { it.title.contains("Molecular Genetics") }
        org.junit.Assert.assertNotNull("Genetics course should exist", genetics)
        assertEquals(com.example.data.model.EducationalCategory.NATURAL_SCIENCES, genetics?.category)

        // History
        val ancientHistory = academicCourses.find { it.title.contains("Ancient") }
        org.junit.Assert.assertNotNull("Ancient Civilizations course should exist", ancientHistory)
        assertEquals(com.example.data.model.EducationalCategory.HISTORY_CIVILIZATION, ancientHistory?.category)

        // Art
        val renaissance = academicCourses.find { it.title.contains("Renaissance") }
        org.junit.Assert.assertNotNull("Renaissance Art course should exist", renaissance)
        assertEquals(com.example.data.model.EducationalCategory.ARTS_HUMANITIES, renaissance?.category)

        // Computer Science
        val distributed = academicCourses.find { it.title.contains("Distributed Systems") }
        org.junit.Assert.assertNotNull("Distributed Systems course should exist", distributed)
        assertEquals(com.example.data.model.EducationalCategory.COMPUTER_SCIENCE, distributed?.category)
    }

    @Test
    fun testDegreeRegistryAndConferralLogic() {
        val programs = com.example.data.model.DegreeRegistry.allDegrees
        assertTrue("Degree registry should define at least 6 degree programs", programs.size >= 6)

        val starters = OfflineCourseTemplates.getCuratedStarterCourses()
        val initialProgresses = com.example.data.model.DegreeRegistry.calculateAllDegreeProgresses(starters)
        assertEquals(programs.size, initialProgresses.size)

        // Simulate student completing and passing all courses for Bachelor of Science in Computer Science
        val csProgram = programs.find { it.id == "degree-cs" }!!
        val passedCsCourses = csProgram.requiredCourseTitles.mapIndexed { idx, title ->
            val match = starters.find { it.title.contains(title, ignoreCase = true) }
                ?: starters.first().copy(id = "synth_cs_$idx", title = title)
            match.copy(
                isCompleted = true,
                bestScore = 95 // 95% Summa Cum Laude
            )
        }

        val csProgress = com.example.data.model.DegreeRegistry.calculateDegreeProgress(csProgram, passedCsCourses)
        assertTrue("Degree should be conferred when all required courses pass >= 70%", csProgress.isConferred)
        assertEquals(csProgram.requiredCourseTitles.size, csProgress.completedCount)
        assertEquals(1.0f, csProgress.progressPercentage, 0.01f)
        assertTrue(
            "Honors designation should contain Summa Cum Laude",
            csProgress.honorsDesignation?.contains("Summa Cum Laude") == true
        )
    }

    @Test
    fun testFreeCertificationsAcrossAcademicSubjects() {
        val allCerts = com.example.data.model.FreeCertificationsData.allCertifications
        assertTrue("Expected at least 15 comprehensive free certifications", allCerts.size >= 15)

        // Check coverage across academic disciplines
        assertTrue(allCerts.any { it.category == com.example.data.model.EducationalCategory.COMPUTER_SCIENCE })
        assertTrue(allCerts.any { it.category == com.example.data.model.EducationalCategory.MATHEMATICS })
        assertTrue(allCerts.any { it.category == com.example.data.model.EducationalCategory.NATURAL_SCIENCES })
        assertTrue(allCerts.any { it.category == com.example.data.model.EducationalCategory.HISTORY_CIVILIZATION })
        assertTrue(allCerts.any { it.category == com.example.data.model.EducationalCategory.ARTS_HUMANITIES })
        assertTrue(allCerts.any { it.category == com.example.data.model.EducationalCategory.SKILLED_TRADES })
        assertTrue(allCerts.any { it.category == com.example.data.model.EducationalCategory.HEALTHCARE_SAFETY })

        // Check verification URLs and titles
        allCerts.forEach { cert ->
            assertTrue("Certification must have valid provider URL", cert.officialUrl.startsWith("http"))
            assertTrue("Certification title must be descriptive", cert.title.isNotBlank())
            assertTrue("Issuing organization must be present", cert.issuingOrganization.isNotBlank())
        }
    }
}
