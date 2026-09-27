package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.CourseViewModel
import com.example.ui.UiScreen
import com.example.ui.components.AttemptHistoryDialog
import com.example.ui.components.ExportCourseDialog
import com.example.ui.components.FreeCertificationsSheet
import com.example.ui.components.ImportCourseDialog
import com.example.ui.screens.CourseCreateScreen
import com.example.ui.screens.CourseDetailScreen
import com.example.ui.screens.CourseHistoryScreen
import com.example.ui.screens.CourseListScreen
import com.example.ui.screens.DegreeCurriculaScreen
import com.example.ui.screens.ExamResultScreen
import com.example.ui.screens.ExamScreen
import com.example.ui.screens.FlashcardDeckScreen
import com.example.ui.screens.SectionReadingScreen
import com.example.ui.screens.dashboard.DashboardCommandCenterScreen
import com.example.ui.theme.AppThemeProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppThemeProvider {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    CourseGeneratorApp()
                }
            }
        }
    }
}

@Composable
fun CourseGeneratorApp(viewModel: CourseViewModel = viewModel()) {
    val context = LocalContext.current
    val courses by viewModel.courses.collectAsState()
    val allAttempts by viewModel.allAttempts.collectAsState()
    val allFlashcards by viewModel.allFlashcards.collectAsState()
    val allSections by viewModel.allSections.collectAsState()
    val degreeProgresses by viewModel.degreeProgresses.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val activeCourse by viewModel.activeCourse.collectAsState()
    val activeSections by viewModel.activeSections.collectAsState()
    val activeFlashcards by viewModel.activeFlashcards.collectAsState()
    val activeAttempts by viewModel.activeAttempts.collectAsState()
    val generationState by viewModel.generationState.collectAsState()
    val userExamAnswers by viewModel.userExamAnswers.collectAsState()
    val exportJson by viewModel.exportJson.collectAsState()
    val userFeedbackMessage by viewModel.userFeedbackMessage.collectAsState()

    var showImportDialog by remember { mutableStateOf(false) }
    var showAttemptsDialog by remember { mutableStateOf(false) }
    var showCertificationsDialog by remember { mutableStateOf(false) }

    LaunchedEffect(userFeedbackMessage) {
        userFeedbackMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearUserFeedback()
        }
    }

    // Handle system back navigation
    when (val screen = currentScreen) {
        is UiScreen.Dashboard -> {
            // Default system back exits app
        }
        is UiScreen.CourseList -> {
            BackHandler {
                viewModel.navigateTo(UiScreen.Dashboard)
            }
        }
        is UiScreen.CreateCourse -> {
            BackHandler(enabled = !generationState.isGenerating) {
                viewModel.navigateTo(UiScreen.CourseList)
            }
        }
        is UiScreen.CourseDetail -> {
            BackHandler {
                viewModel.navigateTo(UiScreen.CourseList)
            }
        }
        is UiScreen.SectionReading -> {
            BackHandler {
                viewModel.navigateTo(UiScreen.CourseDetail(screen.courseId))
            }
        }
        is UiScreen.FlashcardDeck -> {
            BackHandler {
                viewModel.navigateTo(UiScreen.CourseDetail(screen.courseId))
            }
        }
        is UiScreen.Exam -> {
            BackHandler {
                viewModel.navigateTo(UiScreen.CourseDetail(screen.courseId))
            }
        }
        is UiScreen.ExamScorecard -> {
            BackHandler {
                viewModel.navigateTo(UiScreen.CourseDetail(screen.courseId))
            }
        }
        is UiScreen.CourseHistory -> {
            BackHandler {
                viewModel.navigateTo(UiScreen.Dashboard)
            }
        }
        is UiScreen.DegreeCurricula -> {
            BackHandler {
                viewModel.navigateTo(UiScreen.Dashboard)
            }
        }
        is UiScreen.DegreeDetail -> {
            BackHandler {
                viewModel.navigateTo(UiScreen.DegreeCurricula)
            }
        }
    }

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "screen_navigation_transition"
    ) { screen ->
        when (screen) {
            is UiScreen.Dashboard -> {
                DashboardCommandCenterScreen(
                    courses = courses,
                    degreeProgresses = degreeProgresses,
                    allAttempts = allAttempts,
                    onNavigate = { viewModel.navigateTo(it) },
                    onGenerateCourse = { topic ->
                        viewModel.generateCourse(
                            topic,
                            com.example.data.model.ProficiencyLevel.BEGINNER,
                            com.example.data.model.CourseDuration.STANDARD
                        )
                    },
                    onOpenFreeCertsDialog = { showCertificationsDialog = true }
                )
            }

            is UiScreen.CourseList -> {
                CourseListScreen(
                    courses = courses,
                    onNavigate = { viewModel.navigateTo(it) },
                    onExportCourse = { viewModel.prepareExport(it) },
                    onDeleteCourse = { viewModel.deleteCourse(it) },
                    onOpenImportDialog = { showImportDialog = true }
                )
            }

            is UiScreen.CreateCourse -> {
                CourseCreateScreen(
                    generationState = generationState,
                    onBack = { viewModel.navigateTo(UiScreen.CourseList) },
                    onGenerateCourse = { topic, proficiency, duration ->
                        viewModel.generateCourse(topic, proficiency, duration)
                    },
                    onClearError = { viewModel.clearGenerationError() }
                )
            }

            is UiScreen.CourseDetail -> {
                activeCourse?.let { course ->
                    CourseDetailScreen(
                        course = course,
                        sections = activeSections,
                        flashcards = activeFlashcards,
                        attempts = activeAttempts,
                        onBack = { viewModel.navigateTo(UiScreen.CourseList) },
                        onNavigate = { viewModel.navigateTo(it) },
                        onExportCourse = { viewModel.prepareExport(course.id) },
                        onOpenAttemptsDialog = { showAttemptsDialog = true }
                    )
                }
            }

            is UiScreen.SectionReading -> {
                activeCourse?.let { course ->
                    SectionReadingScreen(
                        courseTitle = course.title,
                        sections = activeSections,
                        currentSectionIndex = screen.sectionIndex,
                        onBack = { viewModel.navigateTo(UiScreen.CourseDetail(screen.courseId)) },
                        onNavigateSection = { newIdx ->
                            viewModel.navigateTo(UiScreen.SectionReading(screen.courseId, newIdx))
                        },
                        onToggleSectionCompleted = { secId, completed ->
                            viewModel.markSectionCompleted(screen.courseId, secId, completed)
                        }
                    )
                }
            }

            is UiScreen.FlashcardDeck -> {
                activeCourse?.let { course ->
                    FlashcardDeckScreen(
                        courseTitle = course.title,
                        flashcards = activeFlashcards,
                        onBack = { viewModel.navigateTo(UiScreen.CourseDetail(screen.courseId)) },
                        onSetMastered = { cardId, mastered ->
                            viewModel.setFlashcardMastery(cardId, mastered)
                        }
                    )
                }
            }

            is UiScreen.Exam -> {
                activeCourse?.let { course ->
                    ExamScreen(
                        course = course,
                        questions = course.examQuestions,
                        userAnswers = userExamAnswers,
                        onSelectAnswer = { qIdx, optIdx ->
                            viewModel.selectExamAnswer(qIdx, optIdx)
                        },
                        onSubmitExam = {
                            viewModel.submitExam(screen.courseId)
                        },
                        onBack = { viewModel.navigateTo(UiScreen.CourseDetail(screen.courseId)) }
                    )
                }
            }

            is UiScreen.ExamScorecard -> {
                val scorecardCourse = courses.find { it.id == screen.courseId } ?: activeCourse
                scorecardCourse?.let { course ->
                    ExamResultScreen(
                        course = course,
                        attempt = screen.attempt,
                        onRetakeExam = {
                            viewModel.navigateTo(UiScreen.Exam(screen.courseId))
                        },
                        onReturnToCourse = {
                            viewModel.navigateTo(UiScreen.CourseDetail(screen.courseId))
                        },
                        onViewCourseHistory = {
                            viewModel.navigateTo(UiScreen.CourseHistory)
                        },
                        onViewDegreeCurricula = {
                            viewModel.navigateTo(UiScreen.DegreeCurricula)
                        }
                    )
                }
            }

            is UiScreen.CourseHistory -> {
                CourseHistoryScreen(
                    courses = courses,
                    allAttempts = allAttempts,
                    allFlashcards = allFlashcards,
                    allSections = allSections,
                    onSelectAttempt = { courseId, attempt ->
                        viewModel.loadCourse(courseId)
                        viewModel.navigateTo(UiScreen.ExamScorecard(courseId, attempt))
                    },
                    onSelectCourse = { courseId ->
                        viewModel.navigateTo(UiScreen.CourseDetail(courseId))
                    },
                    onBack = { viewModel.navigateTo(UiScreen.CourseList) }
                )
            }

            is UiScreen.DegreeCurricula -> {
                DegreeCurriculaScreen(
                    degreeProgresses = degreeProgresses,
                    allCourses = courses,
                    onSelectCourse = { courseId ->
                        viewModel.navigateTo(UiScreen.CourseDetail(courseId))
                    },
                    onBack = { viewModel.navigateTo(UiScreen.CourseList) },
                    onGenerateCourse = { topic ->
                        viewModel.generateCourse(
                            topic,
                            com.example.data.model.ProficiencyLevel.INTERMEDIATE,
                            com.example.data.model.CourseDuration.STANDARD
                        )
                    }
                )
            }

            is UiScreen.DegreeDetail -> {
                DegreeCurriculaScreen(
                    degreeProgresses = degreeProgresses,
                    allCourses = courses,
                    onSelectCourse = { courseId ->
                        viewModel.navigateTo(UiScreen.CourseDetail(courseId))
                    },
                    onBack = { viewModel.navigateTo(UiScreen.CourseList) },
                    onGenerateCourse = { topic ->
                        viewModel.generateCourse(
                            topic,
                            com.example.data.model.ProficiencyLevel.INTERMEDIATE,
                            com.example.data.model.CourseDuration.STANDARD
                        )
                    }
                )
            }
        }
    }

    // Dialogs
    exportJson?.let { json ->
        ExportCourseDialog(
            jsonString = json,
            onDismiss = { viewModel.clearExport() }
        )
    }

    if (showImportDialog) {
        ImportCourseDialog(
            onImport = { json ->
                viewModel.importCourse(json) { success ->
                    if (success) {
                        showImportDialog = false
                    }
                }
            },
            onDismiss = { showImportDialog = false }
        )
    }

    if (showAttemptsDialog) {
        AttemptHistoryDialog(
            attempts = activeAttempts,
            onDismiss = { showAttemptsDialog = false }
        )
    }

    if (showCertificationsDialog) {
        FreeCertificationsSheet(
            onDismiss = { showCertificationsDialog = false },
            courses = courses,
            onSelectCourse = { courseId ->
                showCertificationsDialog = false
                viewModel.navigateTo(UiScreen.CourseDetail(courseId))
            }
        )
    }
}
