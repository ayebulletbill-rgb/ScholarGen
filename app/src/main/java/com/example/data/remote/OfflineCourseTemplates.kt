package com.example.data.remote

import com.example.data.model.ActiveRecallCheckpoint
import com.example.data.model.Course
import com.example.data.model.CourseDuration
import com.example.data.model.CourseSection
import com.example.data.model.ExamQuestion
import com.example.data.model.Flashcard
import com.example.data.model.FreeCertificationsData
import com.example.data.model.ProficiencyLevel
import com.example.data.model.ScopeTier
import java.util.UUID

object OfflineCourseTemplates {

    fun generateDynamicOfflineCourse(
        topic: String,
        proficiencyLevel: ProficiencyLevel,
        duration: CourseDuration
    ): Course {
        val courseId = UUID.randomUUID().toString()
        val formattedTopic = topic.trim().replaceFirstChar { it.uppercase() }
        val sectionCount = duration.targetSections

        val allMasterTitles = listOf(
            "Foundations & Epistemology of $formattedTopic",
            "Theoretical Axioms & Conceptual Mechanics",
            "Operational Workflows & Core Paradigms",
            "Methodological Implementation & Frameworks",
            "Diagnostic Metrics & Performance Analysis",
            "Risk Mitigation, Failure Modes & Edge Cases",
            "Integration with Adjacent Systems & Ecosystems",
            "Advanced Real-World Deployments & Case Studies",
            "Professional Invariants & Compliance Standards",
            "Capstone Synthesis & Future Frontiers"
        )

        val sectionTitles = allMasterTitles.take(sectionCount)

        val sections = sectionTitles.mapIndexed { index, secTitle ->
            val secId = UUID.randomUUID().toString()
            CourseSection(
                id = secId,
                courseId = courseId,
                orderIndex = index,
                title = secTitle,
                content = """
                    Welcome to Module ${index + 1}: $secTitle.

                    Tailored for ${proficiencyLevel.title} proficiency: In this module, we examine the fundamental structures and critical dynamics underlying $formattedTopic. Rigorous mastery requires breaking down complex mechanics into actionable mental models.

                    Key Architectural Overview:
                    1. Primary Thesis: $formattedTopic relies upon structured principles that ensure consistency, reproducibility, and high fidelity.
                    2. Methodological Rigor: Every stage of execution must validate assumptions through empirical checkpoints and baseline indicators.
                    3. Practical Synthesis: Theory translates into tangible efficacy when practitioners understand boundary constraints and failure states.

                    Consider how these concepts interact with broader systemic goals. Understanding not merely 'how' to apply techniques, but 'why' specific trade-offs exist distinguishes novice practitioners from subject matter leaders.
                """.trimIndent(),
                takeaways = listOf(
                    "Mastery of $secTitle demands understanding both primary principles and boundary limitations.",
                    "Empirical validation and structured methodologies mitigate common errors in $formattedTopic.",
                    "Applying deliberate trade-off analysis ensures robust, resilient, and reproducible outcomes."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "In the context of $secTitle, what strategy yields the most reliable outcomes for ${proficiencyLevel.title} practitioners?",
                    options = listOf(
                        "Employing empirical checkpoints and deliberate trade-off analysis",
                        "Relying exclusively on untested assumptions without validation",
                        "Ignoring boundary constraints and failure states",
                        "Bypassing architectural reviews to rush deployment"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Employing empirical validation and explicit trade-off analysis ensures system resilience and reproducible performance."
                ),
                isCompleted = false
            )
        }

        val flashcards = listOf(
            Flashcard(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                term = "$formattedTopic Axiom",
                definition = "The irreducible fundamental premise upon which all higher-order methodologies in $formattedTopic are constructed.",
                isMastered = false
            ),
            Flashcard(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                term = "Heuristic Validation",
                definition = "A rapid, rule-of-thumb evaluation protocol to verify that $formattedTopic implementations remain within safe operational bounds.",
                isMastered = false
            ),
            Flashcard(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                term = "Invariant State",
                definition = "A critical condition or truth that must remain unchanged throughout every transformation cycle in the system.",
                isMastered = false
            ),
            Flashcard(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                term = "Operational Throughput",
                definition = "The volume of verified, error-free output generated across consecutive stages of $formattedTopic execution.",
                isMastered = false
            ),
            Flashcard(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                term = "Resilience Vector",
                definition = "The specific architectural pathway or defense mechanism that buffers against sudden variance or external disruption.",
                isMastered = false
            ),
            Flashcard(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                term = "Synthesis Milestone",
                definition = "The convergence point where disparate foundational components coalesce into unified functional mastery.",
                isMastered = false
            )
        )

        val examQuestions = (0 until sectionCount.coerceAtLeast(6)).map { i ->
            val associatedSection = sections[i % sections.size]
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = associatedSection.orderIndex,
                question = "Assessment Question ${i + 1}: How should a ${proficiencyLevel.title} specialist evaluate results in '${associatedSection.title}'?",
                options = listOf(
                    "Through systematic verification against baseline invariants and measurable criteria",
                    "By relying on intuitive guesswork and disregarding documented data",
                    "By discarding negative feedback and proceeding without calibration",
                    "By assuming that past trends guarantee future outcomes unconditionally"
                ),
                correctOptionIndex = 0,
                explanation = "Systematic verification against established baselines guarantees analytical objectivity and prevents regression."
            )
        }

        val category = FreeCertificationsData.categorizeTopic(formattedTopic, formattedTopic)
        val cert = FreeCertificationsData.findMatchingCertification(formattedTopic, formattedTopic)

        return Course(
            id = courseId,
            title = "$formattedTopic: ${proficiencyLevel.title} (${duration.title})",
            topic = formattedTopic,
            description = "A comprehensive curriculum covering $formattedTopic tailored for ${proficiencyLevel.title} proficiency across ${duration.targetSections} modules (~${duration.estimatedTime}).",
            tier = duration.scopeTier,
            proficiencyLevel = proficiencyLevel,
            duration = duration,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions,
            category = category,
            certification = cert
        )
    }

    fun generateDynamicOfflineCourse(topic: String, tier: ScopeTier): Course {
        val courseId = UUID.randomUUID().toString()
        val formattedTopic = topic.trim().replaceFirstChar { it.uppercase() }

        val sectionCount = when (tier) {
            ScopeTier.SHORT -> 3
            ScopeTier.MEDIUM -> 6
            ScopeTier.LONG -> 9
        }

        val sectionTitles = when (tier) {
            ScopeTier.SHORT -> listOf(
                "Core Foundations & Principles of $formattedTopic",
                "Key Mechanisms, Methodologies & Operations",
                "Practical Applications, Best Practices & Synthesis"
            )
            ScopeTier.MEDIUM -> listOf(
                "Historical Context & Evolution of $formattedTopic",
                "Foundational Theoretical Architecture",
                "Operational Mechanics & Core Tools",
                "Methodological Implementation & Workflows",
                "Common Bottlenecks, Anti-patterns & Debugging",
                "Real-World Production Scenarios & Mastery"
            )
            ScopeTier.LONG -> listOf(
                "Introduction & Epistemology of $formattedTopic",
                "Theoretical Axioms & Mathematical/Conceptual Basis",
                "Systemic Architecture & Component Interactions",
                "Operational Workflows & Implementation Paradigms",
                "Analytical Evaluation & Performance Metrics",
                "Risk Factors, Failure Modes & Edge Cases",
                "Integration with Adjacent Disciplines & Ecosystems",
                "Advanced Case Studies & Real-World Deployments",
                "Future Frontiers, Research Directions & Capstone Insights"
            )
        }

        val sections = sectionTitles.mapIndexed { index, secTitle ->
            val secId = UUID.randomUUID().toString()
            CourseSection(
                id = secId,
                courseId = courseId,
                orderIndex = index,
                title = secTitle,
                content = """
                    Welcome to Module ${index + 1}: $secTitle.

                    In this module, we examine the fundamental structures and critical dynamics underlying $formattedTopic. Rigorous mastery requires breaking down complex mechanics into actionable mental models.

                    Key Architectural Overview:
                    1. Primary Thesis: $formattedTopic relies upon structured principles that ensure consistency, reproducibility, and high fidelity.
                    2. Methodological Rigor: Every stage of execution must validate assumptions through empirical checkpoints and baseline indicators.
                    3. Practical Synthesis: Theory translates into tangible efficacy when engineers and researchers understand boundary constraints and failure states.

                    Consider how these concepts interact with broader systemic goals. Understanding not merely 'how' to apply techniques, but 'why' specific trade-offs exist distinguishes novice practitioners from subject matter leaders.
                """.trimIndent(),
                takeaways = listOf(
                    "Mastery of $secTitle demands understanding both primary principles and boundary limitations.",
                    "Empirical validation and structured methodologies mitigate common errors in $formattedTopic.",
                    "Applying deliberate trade-off analysis ensures robust, resilient, and reproducible outcomes."
                ),
                checkpoint = ActiveRecallCheckpoint(
                    question = "In the context of $secTitle, what strategy yields the most reliable outcomes?",
                    options = listOf(
                        "Employing empirical checkpoints and deliberate trade-off analysis",
                        "Relying exclusively on untested assumptions without validation",
                        "Ignoring boundary constraints and failure states",
                        "Bypassing architectural reviews to rush deployment"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Employing empirical validation and explicit trade-off analysis ensures system resilience and reproducible performance."
                ),
                isCompleted = false
            )
        }

        val flashcards = listOf(
            Flashcard(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                term = "$formattedTopic Axiom",
                definition = "The irreducible fundamental premise upon which all higher-order methodologies in $formattedTopic are constructed.",
                isMastered = false
            ),
            Flashcard(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                term = "Heuristic Validation",
                definition = "A rapid, rule-of-thumb evaluation protocol to verify that $formattedTopic implementations remain within safe operational bounds.",
                isMastered = false
            ),
            Flashcard(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                term = "Invariant State",
                definition = "A critical condition or truth that must remain unchanged throughout every transformation cycle in the system.",
                isMastered = false
            ),
            Flashcard(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                term = "Operational Throughput",
                definition = "The volume of verified, error-free output generated across consecutive stages of $formattedTopic execution.",
                isMastered = false
            ),
            Flashcard(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                term = "Resilience Vector",
                definition = "The specific architectural pathway or defense mechanism that buffers against sudden variance or external disruption.",
                isMastered = false
            ),
            Flashcard(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                term = "Synthesis Milestone",
                definition = "The convergence point where disparate foundational components coalesce into unified functional mastery.",
                isMastered = false
            )
        )

        val examQuestions = (0 until sectionCount.coerceAtLeast(6)).map { i ->
            val associatedSection = sections[i % sections.size]
            ExamQuestion(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                sectionIndex = associatedSection.orderIndex,
                question = "Assessment Question ${i + 1}: How should a specialist evaluate results in '${associatedSection.title}'?",
                options = listOf(
                    "Through systematic verification against baseline invariants and measurable criteria",
                    "By relying on intuitive guesswork and disregarding documented data",
                    "By discarding negative feedback and proceeding without calibration",
                    "By assuming that past trends guarantee future outcomes unconditionally"
                ),
                correctOptionIndex = 0,
                explanation = "Systematic verification against established baselines guarantees analytical objectivity and prevents regression."
            )
        }

        return Course(
            id = courseId,
            title = "$formattedTopic: ${tier.title} Mastery",
            topic = formattedTopic,
            description = "A structured, self-paced curriculum covering $formattedTopic across ${tier.targetSections} comprehensive modules.",
            tier = tier,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions
        )
    }

    fun getCuratedStarterCourses(): List<Course> {
        val trades = listOf(
            SkilledTradesCourseTemplates.createMillwrightCourse(),
            SkilledTradesCourseTemplates.createHvacCourse(),
            SkilledTradesCourseTemplates.createElectricianCourse(),
            SkilledTradesCourseTemplates.createCarpentryCourse(),
            SkilledTradesCourseTemplates.createWeldingCourse(),
            SkilledTradesCourseTemplates.createFirstAidCourse(),
            generateDynamicOfflineCourse("Modern Android Architecture with Compose", ScopeTier.MEDIUM),
            generateDynamicOfflineCourse("Compilers & Programming Language Theory", ScopeTier.MEDIUM),
            generateDynamicOfflineCourse("Deep Learning & Transformer Architectures", ScopeTier.LONG)
        )
        val academics = AcademicCourseTemplates.getAllAcademicStarterCourses()
        return trades + academics
    }
}
