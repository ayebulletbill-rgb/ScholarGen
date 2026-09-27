package com.example.data.repository

import android.content.Context
import com.example.data.local.CourseDao
import com.example.data.local.CourseDatabase
import com.example.data.local.CourseEntity
import com.example.data.local.ExamQuestionDao
import com.example.data.local.ExamQuestionEntity
import com.example.data.local.FlashcardDao
import com.example.data.local.FlashcardEntity
import com.example.data.local.SectionDao
import com.example.data.local.SectionEntity
import com.example.data.local.TestAttemptDao
import com.example.data.local.TestAttemptEntity
import com.example.data.model.Course
import com.example.data.model.CourseDuration
import com.example.data.model.CourseSection
import com.example.data.model.Flashcard
import com.example.data.model.GradeResult
import com.example.data.model.ProficiencyLevel
import com.example.data.model.QuestionFeedback
import com.example.data.model.ScopeTier
import com.example.data.model.TestAttempt
import com.example.data.remote.GeminiCourseGenerator
import com.example.data.remote.OfflineCourseTemplates
import com.example.ui.components.CourseThumbnailGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

class CourseRepository(
    private val courseDao: CourseDao,
    private val sectionDao: SectionDao,
    private val flashcardDao: FlashcardDao,
    private val examQuestionDao: ExamQuestionDao,
    private val testAttemptDao: TestAttemptDao,
    private val generator: GeminiCourseGenerator = GeminiCourseGenerator(),
    private val context: Context? = null
) {

    fun getAllCoursesFlow(): Flow<List<Course>> {
        return courseDao.getAllCoursesFlow().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getFullCourse(courseId: String): Course? = withContext(Dispatchers.IO) {
        val courseEntity = courseDao.getCourseById(courseId) ?: return@withContext null
        val sections = sectionDao.getSectionsForCourse(courseId).map { it.toDomain() }
        val flashcards = flashcardDao.getFlashcardsForCourse(courseId).map { it.toDomain() }
        val examQuestions = examQuestionDao.getQuestionsForCourse(courseId).map { it.toDomain() }

        courseEntity.toDomain(
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions
        )
    }

    fun getSectionsFlow(courseId: String) = sectionDao.getSectionsForCourseFlow(courseId).map { list ->
        list.map { it.toDomain() }
    }

    fun getFlashcardsFlow(courseId: String) = flashcardDao.getFlashcardsForCourseFlow(courseId).map { list ->
        list.map { it.toDomain() }
    }

    fun getAttemptsFlow(courseId: String) = testAttemptDao.getAttemptsForCourseFlow(courseId).map { list ->
        list.map { it.toDomain() }
    }

    fun getAllAttemptsFlow(): Flow<List<TestAttempt>> = testAttemptDao.getAllAttemptsFlow().map { list ->
        list.map { it.toDomain() }
    }

    fun getAllFlashcardsFlow(): Flow<List<Flashcard>> = flashcardDao.getAllFlashcardsFlow().map { list ->
        list.map { it.toDomain() }
    }

    fun getAllSectionsFlow(): Flow<List<CourseSection>> = sectionDao.getAllSectionsFlow().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun generateAndSaveCourse(
        topic: String,
        proficiencyLevel: ProficiencyLevel,
        duration: CourseDuration,
        onStatusUpdate: (String) -> Unit
    ): Result<Course> = withContext(Dispatchers.IO) {
        val genResult = generator.generateCourse(topic, proficiencyLevel, duration, onStatusUpdate)
        if (genResult.isFailure) {
            return@withContext genResult
        }

        val course = genResult.getOrThrow()
        saveFullCourse(course)
        Result.success(course)
    }

    suspend fun generateAndSaveCourse(
        topic: String,
        tier: ScopeTier,
        onStatusUpdate: (String) -> Unit
    ): Result<Course> = withContext(Dispatchers.IO) {
        val duration = when (tier) {
            ScopeTier.SHORT -> CourseDuration.CRASH_COURSE
            ScopeTier.MEDIUM -> CourseDuration.STANDARD
            ScopeTier.LONG -> CourseDuration.DEEP_DIVE
        }
        generateAndSaveCourse(topic, ProficiencyLevel.INTERMEDIATE, duration, onStatusUpdate)
    }

    suspend fun saveFullCourse(course: Course) = withContext(Dispatchers.IO) {
        val courseWithThumbnail = if (course.thumbnailUri.isNullOrBlank() && context != null) {
            val thumb = CourseThumbnailGenerator.generateAndSaveThumbnail(
                context = context,
                courseId = course.id,
                title = course.title,
                topic = course.topic,
                category = course.category
            )
            course.copy(thumbnailUri = thumb)
        } else {
            course
        }
        courseDao.insertCourse(CourseEntity.fromDomain(courseWithThumbnail))
        sectionDao.insertSections(courseWithThumbnail.sections.map { SectionEntity.fromDomain(it) })
        flashcardDao.insertFlashcards(courseWithThumbnail.flashcards.map { FlashcardEntity.fromDomain(it) })
        examQuestionDao.insertQuestions(courseWithThumbnail.examQuestions.map { ExamQuestionEntity.fromDomain(it) })
    }

    suspend fun updateSectionCompleted(sectionId: String, completed: Boolean) = withContext(Dispatchers.IO) {
        sectionDao.updateSectionCompleted(sectionId, completed)
    }

    suspend fun updateFlashcardMastery(cardId: String, isMastered: Boolean) = withContext(Dispatchers.IO) {
        flashcardDao.updateMasteredStatus(cardId, isMastered)
    }

    suspend fun recordExamAttempt(
        courseId: String,
        userAnswers: Map<Int, Int> // questionIndex -> selectedOptionIndex
    ): TestAttempt = withContext(Dispatchers.IO) {
        val questions = examQuestionDao.getQuestionsForCourse(courseId).map { it.toDomain() }
        val currentAttemptCount = testAttemptDao.getAttemptCountForCourse(courseId)
        val attemptNumber = currentAttemptCount + 1

        var correctCount = 0
        val feedbacks = questions.mapIndexed { index, question ->
            val selectedOption = userAnswers[index] ?: -1
            val isCorrect = selectedOption == question.correctOptionIndex
            if (isCorrect) correctCount++

            val selectedText = if (selectedOption in question.options.indices) {
                question.options[selectedOption]
            } else {
                "Unanswered"
            }
            val correctText = if (question.correctOptionIndex in question.options.indices) {
                question.options[question.correctOptionIndex]
            } else {
                "N/A"
            }

            val diagnostic = if (isCorrect) {
                "Correct! ${question.explanation}"
            } else {
                "Incorrect. You selected '$selectedText'. The accurate concept is '$correctText'. Reason: ${question.explanation}"
            }

            QuestionFeedback(
                questionId = question.id,
                questionText = question.question,
                selectedOptionIndex = selectedOption,
                correctOptionIndex = question.correctOptionIndex,
                isCorrect = isCorrect,
                selectedAnswerText = selectedText,
                correctAnswerText = correctText,
                diagnosticExplanation = diagnostic
            )
        }

        val totalQuestions = questions.size.coerceAtLeast(1)
        val gradeResult = GradeResult.calculate(correctCount, totalQuestions)

        val attempt = TestAttempt(
            id = UUID.randomUUID().toString(),
            courseId = courseId,
            attemptNumber = attemptNumber,
            timestamp = System.currentTimeMillis(),
            scorePercentage = gradeResult.scorePercentage,
            gradeLetter = gradeResult.gradeLetter,
            totalQuestions = totalQuestions,
            correctCount = correctCount,
            questionFeedbacks = feedbacks
        )

        testAttemptDao.insertAttempt(TestAttemptEntity.fromDomain(attempt))

        // Update course completion and scores
        val currentCourse = courseDao.getCourseById(courseId)
        val currentBest = currentCourse?.bestScore ?: 0
        val newBest = maxOf(currentBest, gradeResult.scorePercentage)
        courseDao.updateCourseScores(courseId, newBest, gradeResult.scorePercentage)
        courseDao.updateCourseCompletion(courseId, true)

        attempt
    }

    suspend fun deleteCourse(courseId: String) = withContext(Dispatchers.IO) {
        testAttemptDao.deleteAttemptsForCourse(courseId)
        examQuestionDao.deleteQuestionsForCourse(courseId)
        flashcardDao.deleteFlashcardsForCourse(courseId)
        sectionDao.deleteSectionsForCourse(courseId)
        courseDao.deleteCourseById(courseId)
    }

    // Export course to JSON payload for sharing
    suspend fun exportCourseJson(courseId: String): String = withContext(Dispatchers.IO) {
        val course = getFullCourse(courseId) ?: throw IllegalStateException("Course not found")

        val root = JSONObject().apply {
            put("version", 1)
            put("id", course.id)
            put("title", course.title)
            put("topic", course.topic)
            put("description", course.description)
            put("tier", course.tier.name)
            put("createdAt", course.createdAt)

            val sectionsArr = JSONArray()
            course.sections.forEach { sec ->
                val sObj = JSONObject().apply {
                    put("id", sec.id)
                    put("orderIndex", sec.orderIndex)
                    put("title", sec.title)
                    put("content", sec.content)

                    val takeawaysArr = JSONArray()
                    sec.takeaways.forEach { takeawaysArr.put(it) }
                    put("takeaways", takeawaysArr)

                    val chkObj = JSONObject().apply {
                        put("question", sec.checkpoint.question)
                        val optsArr = JSONArray()
                        sec.checkpoint.options.forEach { optsArr.put(it) }
                        put("options", optsArr)
                        put("correctOptionIndex", sec.checkpoint.correctOptionIndex)
                        put("explanation", sec.checkpoint.explanation)
                    }
                    put("checkpoint", chkObj)
                }
                sectionsArr.put(sObj)
            }
            put("sections", sectionsArr)

            val flashcardsArr = JSONArray()
            course.flashcards.forEach { fc ->
                val fcObj = JSONObject().apply {
                    put("id", fc.id)
                    put("term", fc.term)
                    put("definition", fc.definition)
                }
                flashcardsArr.put(fcObj)
            }
            put("flashcards", flashcardsArr)

            val examArr = JSONArray()
            course.examQuestions.forEach { eq ->
                val eqObj = JSONObject().apply {
                    put("id", eq.id)
                    put("sectionIndex", eq.sectionIndex)
                    put("question", eq.question)
                    val optsArr = JSONArray()
                    eq.options.forEach { optsArr.put(it) }
                    put("options", optsArr)
                    put("correctOptionIndex", eq.correctOptionIndex)
                    put("explanation", eq.explanation)
                }
                examArr.put(eqObj)
            }
            put("examQuestions", examArr)
        }

        root.toString(2)
    }

    // Import course from JSON payload
    suspend fun importCourseFromJson(jsonString: String): Result<Course> = withContext(Dispatchers.IO) {
        try {
            val root = JSONObject(jsonString)
            val topic = root.optString("topic", "Imported Topic")
            val tierStr = root.optString("tier", ScopeTier.MEDIUM.name)
            val tier = ScopeTier.fromString(tierStr)

            // Re-use parser logic with unique IDs to prevent conflict
            val importedCourse = generator.parseCourseJson(topic, tier, jsonString)
            saveFullCourse(importedCourse)
            Result.success(importedCourse)
        } catch (e: Exception) {
            Result.failure(IllegalArgumentException("Failed to parse course JSON: ${e.localizedMessage}"))
        }
    }

    // Pre-populate with curated starter courses and ensure skilled trades are available
    suspend fun seedInitialCoursesIfEmpty() = withContext(Dispatchers.IO) {
        val existing = courseDao.getAllCoursesFlow().firstOrNull() ?: emptyList()
        val existingIds = existing.map { it.id }.toSet()
        val starters = OfflineCourseTemplates.getCuratedStarterCourses()
        starters.forEach { course ->
            if (course.id !in existingIds) {
                saveFullCourse(course)
            }
        }
    }

    companion object {
        fun provide(context: Context): CourseRepository {
            val db = CourseDatabase.getInstance(context)
            return CourseRepository(
                courseDao = db.courseDao(),
                sectionDao = db.sectionDao(),
                flashcardDao = db.flashcardDao(),
                examQuestionDao = db.examQuestionDao(),
                testAttemptDao = db.testAttemptDao(),
                context = context.applicationContext
            )
        }
    }
}
