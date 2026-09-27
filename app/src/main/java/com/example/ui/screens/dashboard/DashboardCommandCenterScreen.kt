package com.example.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.model.Course
import com.example.data.model.DegreeProgress
import com.example.data.model.DegreeRegistry
import com.example.data.model.FreeCertificationsData
import com.example.data.model.TestAttempt
import com.example.ui.UiScreen
import com.example.ui.theme.DashboardTokens

/**
 * Modular Academy Command Center Dashboard
 * Mimics the high-density reference UI design with:
 * - Base: Black & Dark Grey (#090B0E, #13171D)
 * - Secondary: Dark Green (#0D2B1D, #134E31, #10B981)
 * - Third: Crimson & Coral Red (#DC2626, #EF4444)
 */
@Composable
fun DashboardCommandCenterScreen(
    courses: List<Course>,
    degreeProgresses: List<DegreeProgress>,
    allAttempts: List<TestAttempt>,
    onNavigate: (UiScreen) -> Unit,
    onGenerateCourse: (String) -> Unit,
    onOpenFreeCertsDialog: () -> Unit
) {
    var activeTab by remember { mutableStateOf("Dashboard") }
    val totalCerts = FreeCertificationsData.allCertifications.size
    val totalDegrees = DegreeRegistry.allDegrees.size

    Scaffold(
        containerColor = DashboardTokens.CanvasBase,
        modifier = Modifier
            .fillMaxSize()
            .testTag("dashboard_command_center_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            // 1. Top Navigation Bar (Settings | Account | Certs (5) | Favorites | Me ⌄)
            DashboardHeaderBar(
                selectedTab = activeTab,
                onTabSelected = { tab ->
                    activeTab = tab
                    when (tab) {
                        "Courses" -> onNavigate(UiScreen.CourseList)
                        "Degrees" -> onNavigate(UiScreen.DegreeCurricula)
                        "Certs" -> onOpenFreeCertsDialog()
                        "History" -> onNavigate(UiScreen.CourseHistory)
                        else -> { /* Stay on Dashboard */ }
                    }
                },
                activeAlertCount = totalCerts,
                onProfileClick = { onNavigate(UiScreen.CourseHistory) }
            )

            Spacer(Modifier.height(12.dp))

            // 2. Responsive Dashboard Body
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val isExpanded = maxWidth >= 720.dp

                if (isExpanded) {
                    // Exact 3-Column Layout from Reference Design
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Left Column (Menu Box, Donut Chart, Mastery Trend, Media Preview)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            MenuBoxWidget(
                                courseCount = courses.size,
                                certCount = totalCerts,
                                degreeCount = totalDegrees,
                                onCourseListClick = { onNavigate(UiScreen.CourseList) },
                                onDegreeCurriculaClick = { onNavigate(UiScreen.DegreeCurricula) },
                                onCertificationsClick = onOpenFreeCertsDialog,
                                onHistoryClick = { onNavigate(UiScreen.CourseHistory) }
                            )

                            CurriculumDonutWidget()

                            AcademicTrendChartWidget()

                            LectureMediaPreviewWidget()
                        }

                        // Center Column (Learner Profile, Study Streak Card, Wire Feed, Action Footer)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            LearnerProfileWidget(
                                studentName = "Austin Phipps",
                                degreeMajor = "B.S. in Computer Science & Trades",
                                quizzesCompleted = allAttempts.size.coerceAtLeast(23),
                                studyHours = (courses.size * 32).coerceAtLeast(841),
                                masteredDecks = 49
                            )

                            StudyStreakCardWidget()

                            AcademicFeedWidget()

                            QuickActionFooterWidget(
                                onFlashcardRecallClick = {
                                    if (courses.isNotEmpty()) {
                                        onNavigate(UiScreen.FlashcardDeck(courses.first().id))
                                    } else {
                                        onNavigate(UiScreen.CourseList)
                                    }
                                },
                                onDegreeAuditsClick = { onNavigate(UiScreen.DegreeCurricula) },
                                onFreeCertsClick = onOpenFreeCertsDialog
                            )
                        }

                        // Right Column (Quick Enroll Form, Resource Sync, Calendar Widgets)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            QuickEnrollWidget(onGenerateCourse = onGenerateCourse)

                            ResourceSyncBarsWidget()

                            DayAndCalendarWidget(
                                onScheduleExam = {
                                    if (courses.isNotEmpty()) {
                                        onNavigate(UiScreen.Exam(courses.first().id))
                                    } else {
                                        onNavigate(UiScreen.CourseList)
                                    }
                                }
                            )
                        }
                    }
                } else {
                    // Mobile Portrait Layout (Optimized, balanced 1-column feed)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        LearnerProfileWidget(
                            studentName = "Austin Phipps",
                            degreeMajor = "B.S. in Computer Science & Trades",
                            quizzesCompleted = allAttempts.size.coerceAtLeast(23),
                            studyHours = (courses.size * 32).coerceAtLeast(841),
                            masteredDecks = 49
                        )

                        MenuBoxWidget(
                            courseCount = courses.size,
                            certCount = totalCerts,
                            degreeCount = totalDegrees,
                            onCourseListClick = { onNavigate(UiScreen.CourseList) },
                            onDegreeCurriculaClick = { onNavigate(UiScreen.DegreeCurricula) },
                            onCertificationsClick = onOpenFreeCertsDialog,
                            onHistoryClick = { onNavigate(UiScreen.CourseHistory) }
                        )

                        CurriculumDonutWidget()

                        AcademicTrendChartWidget()

                        StudyStreakCardWidget()

                        DayAndCalendarWidget(
                            onScheduleExam = {
                                if (courses.isNotEmpty()) {
                                    onNavigate(UiScreen.Exam(courses.first().id))
                                } else {
                                    onNavigate(UiScreen.CourseList)
                                }
                            }
                        )

                        ResourceSyncBarsWidget()

                        QuickEnrollWidget(onGenerateCourse = onGenerateCourse)

                        AcademicFeedWidget()

                        LectureMediaPreviewWidget(
                            course = courses.firstOrNull(),
                            onPlayCourse = {
                                courses.firstOrNull()?.let { c ->
                                    onNavigate(UiScreen.CourseDetail(c.id))
                                }
                            }
                        )

                        QuickActionFooterWidget(
                            onFlashcardRecallClick = {
                                if (courses.isNotEmpty()) {
                                    onNavigate(UiScreen.FlashcardDeck(courses.first().id))
                                } else {
                                    onNavigate(UiScreen.CourseList)
                                }
                            },
                            onDegreeAuditsClick = { onNavigate(UiScreen.DegreeCurricula) },
                            onFreeCertsClick = onOpenFreeCertsDialog
                        )
                    }
                }
            }
        }
    }
}
