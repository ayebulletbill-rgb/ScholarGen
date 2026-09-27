package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Course
import com.example.data.model.CourseDuration
import com.example.data.model.CourseSection
import com.example.data.model.DegreeProgress
import com.example.data.model.DegreeRegistry
import com.example.data.model.ExamQuestion
import com.example.data.model.Flashcard
import com.example.data.model.ProficiencyLevel
import com.example.data.model.ScopeTier
import com.example.data.model.TestAttempt
import com.example.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface UiScreen {
    data object Dashboard : UiScreen
    data object CourseList : UiScreen
    data object CreateCourse : UiScreen
    data class CourseDetail(val courseId: String) : UiScreen
    data class SectionReading(val courseId: String, val sectionIndex: Int) : UiScreen
    data class FlashcardDeck(val courseId: String) : UiScreen
    data class Exam(val courseId: String) : UiScreen
    data class ExamScorecard(val courseId: String, val attempt: TestAttempt) : UiScreen
    data object CourseHistory : UiScreen
    data object DegreeCurricula : UiScreen
    data class DegreeDetail(val degreeId: String) : UiScreen
}

data class GenerationState(
    val isGenerating: Boolean = false,
    val progressMessage: String = "",
    val error: String? = null
)

class CourseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CourseRepository.provide(application)

    val courses: StateFlow<List<Course>> = repository.getAllCoursesFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allAttempts: StateFlow<List<TestAttempt>> = repository.getAllAttemptsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allFlashcards: StateFlow<List<Flashcard>> = repository.getAllFlashcardsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allSections: StateFlow<List<CourseSection>> = repository.getAllSectionsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val degreeProgresses: StateFlow<List<DegreeProgress>> = courses.map { courseList ->
        DegreeRegistry.calculateAllDegreeProgresses(courseList)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _currentScreen = MutableStateFlow<UiScreen>(UiScreen.Dashboard)
    val currentScreen: StateFlow<UiScreen> = _currentScreen.asStateFlow()

    private val _activeCourse = MutableStateFlow<Course?>(null)
    val activeCourse: StateFlow<Course?> = _activeCourse.asStateFlow()

    private val _activeSections = MutableStateFlow<List<CourseSection>>(emptyList())
    val activeSections: StateFlow<List<CourseSection>> = _activeSections.asStateFlow()

    private val _activeFlashcards = MutableStateFlow<List<Flashcard>>(emptyList())
    val activeFlashcards: StateFlow<List<Flashcard>> = _activeFlashcards.asStateFlow()

    private val _activeAttempts = MutableStateFlow<List<TestAttempt>>(emptyList())
    val activeAttempts: StateFlow<List<TestAttempt>> = _activeAttempts.asStateFlow()

    private val _generationState = MutableStateFlow(GenerationState())
    val generationState: StateFlow<GenerationState> = _generationState.asStateFlow()

    private val _userExamAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val userExamAnswers: StateFlow<Map<Int, Int>> = _userExamAnswers.asStateFlow()

    private val _exportJson = MutableStateFlow<String?>(null)
    val exportJson: StateFlow<String?> = _exportJson.asStateFlow()

    private val _userFeedbackMessage = MutableStateFlow<String?>(null)
    val userFeedbackMessage: StateFlow<String?> = _userFeedbackMessage.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedInitialCoursesIfEmpty()
        }
    }

    fun navigateTo(screen: UiScreen) {
        _currentScreen.value = screen
        when (screen) {
            is UiScreen.CourseDetail -> loadCourse(screen.courseId)
            is UiScreen.SectionReading -> loadCourse(screen.courseId)
            is UiScreen.FlashcardDeck -> loadCourse(screen.courseId)
            is UiScreen.Exam -> {
                loadCourse(screen.courseId)
                _userExamAnswers.value = emptyMap()
            }
            is UiScreen.ExamScorecard -> loadCourse(screen.courseId)
            else -> {}
        }
    }

    fun loadCourse(courseId: String) {
        viewModelScope.launch {
            val course = repository.getFullCourse(courseId)
            _activeCourse.value = course
            if (course != null) {
                _activeSections.value = course.sections
                _activeFlashcards.value = course.flashcards
            }
            // Load attempts
            repository.getAttemptsFlow(courseId).collect { attempts ->
                _activeAttempts.value = attempts
            }
        }
    }

    fun generateCourse(
        topic: String,
        proficiencyLevel: ProficiencyLevel,
        duration: CourseDuration
    ) {
        if (topic.isBlank()) {
            _generationState.value = GenerationState(error = "Please enter a course topic.")
            return
        }

        viewModelScope.launch {
            _generationState.value = GenerationState(
                isGenerating = true,
                progressMessage = "Synthesizing ${proficiencyLevel.title} curriculum for '$topic' (${duration.title} • ${duration.estimatedTime})..."
            )

            val result = repository.generateAndSaveCourse(topic, proficiencyLevel, duration) { msg ->
                _generationState.value = _generationState.value.copy(progressMessage = msg)
            }

            if (result.isSuccess) {
                val created = result.getOrThrow()
                _generationState.value = GenerationState(isGenerating = false)
                navigateTo(UiScreen.CourseDetail(created.id))
            } else {
                val errorMsg = result.exceptionOrNull()?.localizedMessage ?: "Generation failed."
                _generationState.value = GenerationState(
                    isGenerating = false,
                    error = errorMsg
                )
            }
        }
    }

    fun generateCourse(topic: String, tier: ScopeTier) {
        val duration = when (tier) {
            ScopeTier.SHORT -> CourseDuration.CRASH_COURSE
            ScopeTier.MEDIUM -> CourseDuration.STANDARD
            ScopeTier.LONG -> CourseDuration.DEEP_DIVE
        }
        generateCourse(topic, ProficiencyLevel.INTERMEDIATE, duration)
    }

    fun clearGenerationError() {
        _generationState.value = _generationState.value.copy(error = null)
    }

    fun markSectionCompleted(courseId: String, sectionId: String, completed: Boolean) {
        viewModelScope.launch {
            repository.updateSectionCompleted(sectionId, completed)
            // Reload course
            val course = repository.getFullCourse(courseId)
            _activeCourse.value = course
            if (course != null) {
                _activeSections.value = course.sections
            }
        }
    }

    fun toggleFlashcardMastery(cardId: String, currentStatus: Boolean) {
        viewModelScope.launch {
            val newStatus = !currentStatus
            repository.updateFlashcardMastery(cardId, newStatus)
            _activeCourse.value?.id?.let { courseId ->
                val course = repository.getFullCourse(courseId)
                _activeCourse.value = course
                if (course != null) {
                    _activeFlashcards.value = course.flashcards
                }
            }
        }
    }

    fun setFlashcardMastery(cardId: String, isMastered: Boolean) {
        viewModelScope.launch {
            repository.updateFlashcardMastery(cardId, isMastered)
            _activeCourse.value?.id?.let { courseId ->
                val course = repository.getFullCourse(courseId)
                _activeCourse.value = course
                if (course != null) {
                    _activeFlashcards.value = course.flashcards
                }
            }
        }
    }

    fun selectExamAnswer(questionIndex: Int, optionIndex: Int) {
        val current = _userExamAnswers.value.toMutableMap()
        current[questionIndex] = optionIndex
        _userExamAnswers.value = current
    }

    fun submitExam(courseId: String) {
        viewModelScope.launch {
            val attempt = repository.recordExamAttempt(courseId, _userExamAnswers.value)
            // Reload course
            val course = repository.getFullCourse(courseId)
            _activeCourse.value = course
            navigateTo(UiScreen.ExamScorecard(courseId, attempt))
        }
    }

    fun deleteCourse(courseId: String) {
        viewModelScope.launch {
            repository.deleteCourse(courseId)
            navigateTo(UiScreen.CourseList)
        }
    }

    fun prepareExport(courseId: String) {
        viewModelScope.launch {
            try {
                val json = repository.exportCourseJson(courseId)
                _exportJson.value = json
            } catch (e: Exception) {
                _userFeedbackMessage.value = "Failed to export: ${e.message}"
            }
        }
    }

    fun clearExport() {
        _exportJson.value = null
    }

    fun importCourse(json: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.importCourseFromJson(json)
            if (result.isSuccess) {
                val course = result.getOrThrow()
                _userFeedbackMessage.value = "Successfully imported '${course.title}'"
                navigateTo(UiScreen.CourseDetail(course.id))
                onComplete(true)
            } else {
                _userFeedbackMessage.value = "Import failed: ${result.exceptionOrNull()?.message}"
                onComplete(false)
            }
        }
    }

    fun clearUserFeedback() {
        _userFeedbackMessage.value = null
    }
}
