package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.CourseDuration
import com.example.data.model.ProficiencyLevel
import com.example.data.model.ActiveRecallCheckpoint
import com.example.data.model.Course
import com.example.data.model.CourseSection
import com.example.data.model.ExamQuestion
import com.example.data.model.Flashcard
import com.example.data.model.QuestionFeedback
import com.example.data.model.ScopeTier
import com.example.data.model.TestAttempt
import org.json.JSONArray
import org.json.JSONObject

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val id: String,
    val title: String,
    val topic: String,
    val description: String,
    val tier: String,
    val proficiencyLevel: String = "INTERMEDIATE",
    val duration: String = "STANDARD",
    val createdAt: Long,
    val isCompleted: Boolean,
    val bestScore: Int?,
    val lastScore: Int?,
    val thumbnailUri: String? = null
) {
    fun toDomain(
        sections: List<CourseSection> = emptyList(),
        flashcards: List<Flashcard> = emptyList(),
        examQuestions: List<ExamQuestion> = emptyList()
    ): Course {
        val parsedTier = ScopeTier.fromString(tier)
        val parsedDuration = CourseDuration.fromString(duration)
        val parsedProficiency = ProficiencyLevel.fromString(proficiencyLevel)
        return Course(
            id = id,
            title = title,
            topic = topic,
            description = description,
            tier = parsedTier,
            proficiencyLevel = parsedProficiency,
            duration = parsedDuration,
            createdAt = createdAt,
            isCompleted = isCompleted,
            bestScore = bestScore,
            lastScore = lastScore,
            sections = sections,
            flashcards = flashcards,
            examQuestions = examQuestions,
            thumbnailUri = thumbnailUri
        )
    }

    companion object {
        fun fromDomain(course: Course): CourseEntity {
            return CourseEntity(
                id = course.id,
                title = course.title,
                topic = course.topic,
                description = course.description,
                tier = course.tier.name,
                proficiencyLevel = course.proficiencyLevel.name,
                duration = course.duration.name,
                createdAt = course.createdAt,
                isCompleted = course.isCompleted,
                bestScore = course.bestScore,
                lastScore = course.lastScore,
                thumbnailUri = course.thumbnailUri
            )
        }
    }
}

@Entity(tableName = "sections")
data class SectionEntity(
    @PrimaryKey val id: String,
    val courseId: String,
    val orderIndex: Int,
    val title: String,
    val content: String,
    val takeawaysJson: String,
    val checkpointQuestion: String,
    val checkpointOptionsJson: String,
    val checkpointCorrectIndex: Int,
    val checkpointExplanation: String,
    val isCompleted: Boolean
) {
    fun toDomain(): CourseSection {
        val takeawaysList = mutableListOf<String>()
        try {
            val jsonArray = JSONArray(takeawaysJson)
            for (i in 0 until jsonArray.length()) {
                takeawaysList.add(jsonArray.getString(i))
            }
        } catch (_: Exception) {}

        val optionsList = mutableListOf<String>()
        try {
            val jsonArray = JSONArray(checkpointOptionsJson)
            for (i in 0 until jsonArray.length()) {
                optionsList.add(jsonArray.getString(i))
            }
        } catch (_: Exception) {}

        return CourseSection(
            id = id,
            courseId = courseId,
            orderIndex = orderIndex,
            title = title,
            content = content,
            takeaways = takeawaysList,
            checkpoint = ActiveRecallCheckpoint(
                question = checkpointQuestion,
                options = optionsList,
                correctOptionIndex = checkpointCorrectIndex,
                explanation = checkpointExplanation
            ),
            isCompleted = isCompleted
        )
    }

    companion object {
        fun fromDomain(section: CourseSection): SectionEntity {
            val takeawaysArr = JSONArray()
            section.takeaways.forEach { takeawaysArr.put(it) }

            val optionsArr = JSONArray()
            section.checkpoint.options.forEach { optionsArr.put(it) }

            return SectionEntity(
                id = section.id,
                courseId = section.courseId,
                orderIndex = section.orderIndex,
                title = section.title,
                content = section.content,
                takeawaysJson = takeawaysArr.toString(),
                checkpointQuestion = section.checkpoint.question,
                checkpointOptionsJson = optionsArr.toString(),
                checkpointCorrectIndex = section.checkpoint.correctOptionIndex,
                checkpointExplanation = section.checkpoint.explanation,
                isCompleted = section.isCompleted
            )
        }
    }
}

@Entity(tableName = "flashcards")
data class FlashcardEntity(
    @PrimaryKey val id: String,
    val courseId: String,
    val term: String,
    val definition: String,
    val isMastered: Boolean
) {
    fun toDomain(): Flashcard = Flashcard(
        id = id,
        courseId = courseId,
        term = term,
        definition = definition,
        isMastered = isMastered
    )

    companion object {
        fun fromDomain(card: Flashcard): FlashcardEntity = FlashcardEntity(
            id = card.id,
            courseId = card.courseId,
            term = card.term,
            definition = card.definition,
            isMastered = card.isMastered
        )
    }
}

@Entity(tableName = "exam_questions")
data class ExamQuestionEntity(
    @PrimaryKey val id: String,
    val courseId: String,
    val sectionIndex: Int,
    val question: String,
    val optionsJson: String,
    val correctOptionIndex: Int,
    val explanation: String
) {
    fun toDomain(): ExamQuestion {
        val optionsList = mutableListOf<String>()
        try {
            val jsonArray = JSONArray(optionsJson)
            for (i in 0 until jsonArray.length()) {
                optionsList.add(jsonArray.getString(i))
            }
        } catch (_: Exception) {}

        return ExamQuestion(
            id = id,
            courseId = courseId,
            sectionIndex = sectionIndex,
            question = question,
            options = optionsList,
            correctOptionIndex = correctOptionIndex,
            explanation = explanation
        )
    }

    companion object {
        fun fromDomain(q: ExamQuestion): ExamQuestionEntity {
            val optionsArr = JSONArray()
            q.options.forEach { optionsArr.put(it) }
            return ExamQuestionEntity(
                id = q.id,
                courseId = q.courseId,
                sectionIndex = q.sectionIndex,
                question = q.question,
                optionsJson = optionsArr.toString(),
                correctOptionIndex = q.correctOptionIndex,
                explanation = q.explanation
            )
        }
    }
}

@Entity(tableName = "test_attempts")
data class TestAttemptEntity(
    @PrimaryKey val id: String,
    val courseId: String,
    val attemptNumber: Int,
    val timestamp: Long,
    val scorePercentage: Int,
    val gradeLetter: String,
    val totalQuestions: Int,
    val correctCount: Int,
    val feedbackJson: String
) {
    fun toDomain(): TestAttempt {
        val feedbackList = mutableListOf<QuestionFeedback>()
        try {
            val array = JSONArray(feedbackJson)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                feedbackList.add(
                    QuestionFeedback(
                        questionId = obj.optString("questionId"),
                        questionText = obj.optString("questionText"),
                        selectedOptionIndex = obj.optInt("selectedOptionIndex"),
                        correctOptionIndex = obj.optInt("correctOptionIndex"),
                        isCorrect = obj.optBoolean("isCorrect"),
                        selectedAnswerText = obj.optString("selectedAnswerText"),
                        correctAnswerText = obj.optString("correctAnswerText"),
                        diagnosticExplanation = obj.optString("diagnosticExplanation")
                    )
                )
            }
        } catch (_: Exception) {}

        return TestAttempt(
            id = id,
            courseId = courseId,
            attemptNumber = attemptNumber,
            timestamp = timestamp,
            scorePercentage = scorePercentage,
            gradeLetter = gradeLetter,
            totalQuestions = totalQuestions,
            correctCount = correctCount,
            questionFeedbacks = feedbackList
        )
    }

    companion object {
        fun fromDomain(attempt: TestAttempt): TestAttemptEntity {
            val array = JSONArray()
            attempt.questionFeedbacks.forEach { fb ->
                val obj = JSONObject()
                obj.put("questionId", fb.questionId)
                obj.put("questionText", fb.questionText)
                obj.put("selectedOptionIndex", fb.selectedOptionIndex)
                obj.put("correctOptionIndex", fb.correctOptionIndex)
                obj.put("isCorrect", fb.isCorrect)
                obj.put("selectedAnswerText", fb.selectedAnswerText)
                obj.put("correctAnswerText", fb.correctAnswerText)
                obj.put("diagnosticExplanation", fb.diagnosticExplanation)
                array.put(obj)
            }
            return TestAttemptEntity(
                id = attempt.id,
                courseId = attempt.courseId,
                attemptNumber = attempt.attemptNumber,
                timestamp = attempt.timestamp,
                scorePercentage = attempt.scorePercentage,
                gradeLetter = attempt.gradeLetter,
                totalQuestions = attempt.totalQuestions,
                correctCount = attempt.correctCount,
                feedbackJson = array.toString()
            )
        }
    }
}
