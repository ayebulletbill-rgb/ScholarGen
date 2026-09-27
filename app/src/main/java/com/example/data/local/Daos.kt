package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses ORDER BY createdAt DESC")
    fun getAllCoursesFlow(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE id = :courseId")
    suspend fun getCourseById(courseId: String): CourseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity)

    @Update
    suspend fun updateCourse(course: CourseEntity)

    @Query("UPDATE courses SET isCompleted = :completed WHERE id = :courseId")
    suspend fun updateCourseCompletion(courseId: String, completed: Boolean)

    @Query("UPDATE courses SET bestScore = :bestScore, lastScore = :lastScore WHERE id = :courseId")
    suspend fun updateCourseScores(courseId: String, bestScore: Int, lastScore: Int)

    @Query("DELETE FROM courses WHERE id = :courseId")
    suspend fun deleteCourseById(courseId: String)
}

@Dao
interface SectionDao {
    @Query("SELECT * FROM sections WHERE courseId = :courseId ORDER BY orderIndex ASC")
    fun getSectionsForCourseFlow(courseId: String): Flow<List<SectionEntity>>

    @Query("SELECT * FROM sections WHERE courseId = :courseId ORDER BY orderIndex ASC")
    suspend fun getSectionsForCourse(courseId: String): List<SectionEntity>

    @Query("SELECT * FROM sections WHERE id = :sectionId")
    suspend fun getSectionById(sectionId: String): SectionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSections(sections: List<SectionEntity>)

    @Query("UPDATE sections SET isCompleted = :completed WHERE id = :sectionId")
    suspend fun updateSectionCompleted(sectionId: String, completed: Boolean)

    @Query("SELECT * FROM sections")
    fun getAllSectionsFlow(): Flow<List<SectionEntity>>

    @Query("DELETE FROM sections WHERE courseId = :courseId")
    suspend fun deleteSectionsForCourse(courseId: String)
}

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM flashcards WHERE courseId = :courseId")
    fun getFlashcardsForCourseFlow(courseId: String): Flow<List<FlashcardEntity>>

    @Query("SELECT * FROM flashcards")
    fun getAllFlashcardsFlow(): Flow<List<FlashcardEntity>>

    @Query("SELECT * FROM flashcards WHERE courseId = :courseId")
    suspend fun getFlashcardsForCourse(courseId: String): List<FlashcardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcards(flashcards: List<FlashcardEntity>)

    @Query("UPDATE flashcards SET isMastered = :isMastered WHERE id = :cardId")
    suspend fun updateMasteredStatus(cardId: String, isMastered: Boolean)

    @Query("DELETE FROM flashcards WHERE courseId = :courseId")
    suspend fun deleteFlashcardsForCourse(courseId: String)
}

@Dao
interface ExamQuestionDao {
    @Query("SELECT * FROM exam_questions WHERE courseId = :courseId ORDER BY sectionIndex ASC")
    fun getQuestionsForCourseFlow(courseId: String): Flow<List<ExamQuestionEntity>>

    @Query("SELECT * FROM exam_questions WHERE courseId = :courseId ORDER BY sectionIndex ASC")
    suspend fun getQuestionsForCourse(courseId: String): List<ExamQuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<ExamQuestionEntity>)

    @Query("DELETE FROM exam_questions WHERE courseId = :courseId")
    suspend fun deleteQuestionsForCourse(courseId: String)
}

@Dao
interface TestAttemptDao {
    @Query("SELECT * FROM test_attempts WHERE courseId = :courseId ORDER BY attemptNumber DESC")
    fun getAttemptsForCourseFlow(courseId: String): Flow<List<TestAttemptEntity>>

    @Query("SELECT * FROM test_attempts ORDER BY timestamp DESC")
    fun getAllAttemptsFlow(): Flow<List<TestAttemptEntity>>

    @Query("SELECT * FROM test_attempts WHERE courseId = :courseId ORDER BY attemptNumber DESC")
    suspend fun getAttemptsForCourse(courseId: String): List<TestAttemptEntity>

    @Query("SELECT COUNT(*) FROM test_attempts WHERE courseId = :courseId")
    suspend fun getAttemptCountForCourse(courseId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: TestAttemptEntity)

    @Query("DELETE FROM test_attempts WHERE courseId = :courseId")
    suspend fun deleteAttemptsForCourse(courseId: String)
}
