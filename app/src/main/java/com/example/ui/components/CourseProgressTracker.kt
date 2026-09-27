package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.CourseSection
import com.example.data.model.Flashcard
import com.example.data.model.GradeResult
import com.example.data.model.TestAttempt

/**
 * High-level phase status of an active course.
 */
enum class CoursePhase(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val accentColorHex: Long
) {
    NOT_STARTED(
        title = "Ready to Begin",
        description = "Course initialized. Start Module 1 to begin learning.",
        icon = Icons.Default.PlayArrow,
        accentColorHex = 0xFF1976D2
    ),
    IN_PROGRESS(
        title = "In Progress",
        description = "Active study underway. Complete reading and checkpoint quizzes.",
        icon = Icons.Default.HourglassTop,
        accentColorHex = 0xFFF57C00
    ),
    EXAM_READY(
        title = "Exam Ready",
        description = "All curriculum modules completed! Test your knowledge.",
        icon = Icons.Default.AutoAwesome,
        accentColorHex = 0xFF7B1FA2
    ),
    MASTERED(
        title = "Course Mastered",
        description = "Curriculum and cumulative evaluation successfully passed.",
        icon = Icons.Default.School,
        accentColorHex = 0xFF2E7D32
    )
}

/**
 * State object holding all calculated metrics for tracking progress in an active course.
 */
data class CourseProgressState(
    val completedSections: Int,
    val totalSections: Int,
    val sectionProgress: Float,
    val masteredFlashcards: Int,
    val totalFlashcards: Int,
    val flashcardProgress: Float,
    val examBestScore: Int?,
    val examAttemptsCount: Int,
    val overallProgress: Float,
    val phase: CoursePhase,
    val nextActionLabel: String,
    val nextActionSubtitle: String,
    val nextModuleIndex: Int?
) {
    companion object {
        fun fromCourseData(
            course: Course,
            sections: List<CourseSection>,
            flashcards: List<Flashcard>,
            attempts: List<TestAttempt>
        ): CourseProgressState {
            val totalSec = sections.size.coerceAtLeast(1)
            val compSec = sections.count { it.isCompleted }
            val secProg = (compSec.toFloat() / totalSec.toFloat()).coerceIn(0f, 1f)

            val totalCards = flashcards.size
            val mastCards = flashcards.count { it.isMastered }
            val cardProg = if (totalCards > 0) (mastCards.toFloat() / totalCards.toFloat()).coerceIn(0f, 1f) else 0f

            val bestScore = course.bestScore ?: attempts.maxOfOrNull { it.scorePercentage }
            val attemptsCount = attempts.size

            // Overall weighted completion: 60% sections + 20% flashcards + 20% exam score/completion
            val examWeightProgress = when {
                bestScore != null -> (bestScore.toFloat() / 100f).coerceIn(0f, 1f)
                else -> 0f
            }
            val overallProg = ((secProg * 0.60f) + (cardProg * 0.20f) + (examWeightProgress * 0.20f)).coerceIn(0f, 1f)

            // Determine Phase
            val phase = when {
                compSec == totalSec && (bestScore != null && bestScore >= 70) -> CoursePhase.MASTERED
                compSec == totalSec -> CoursePhase.EXAM_READY
                compSec > 0 -> CoursePhase.IN_PROGRESS
                else -> CoursePhase.NOT_STARTED
            }

            // Find next uncompleted section
            val nextUncompletedIndex = sections.indexOfFirst { !it.isCompleted }.takeIf { it >= 0 }

            val (actionLabel, actionSubtitle) = when {
                nextUncompletedIndex != null -> {
                    val nextSection = sections[nextUncompletedIndex]
                    Pair(
                        "Resume Module ${nextUncompletedIndex + 1}",
                        nextSection.title
                    )
                }
                attemptsCount == 0 -> {
                    Pair("Take Final Exam", "Modules completed. Test your knowledge on the cumulative exam.")
                }
                bestScore != null && bestScore < 70 -> {
                    Pair("Retake Exam", "Previous score: $bestScore%. Drill flashcards & retake to achieve mastery.")
                }
                cardProg < 1.0f && totalCards > 0 -> {
                    Pair("Review Flashcards", "${totalCards - mastCards} flashcard(s) remaining to master.")
                }
                else -> {
                    Pair("Review Course", "Mastery achieved! Review key takeaways or retake exam anytime.")
                }
            }

            return CourseProgressState(
                completedSections = compSec,
                totalSections = totalSec,
                sectionProgress = secProg,
                masteredFlashcards = mastCards,
                totalFlashcards = totalCards,
                flashcardProgress = cardProg,
                examBestScore = bestScore,
                examAttemptsCount = attemptsCount,
                overallProgress = overallProg,
                phase = phase,
                nextActionLabel = actionLabel,
                nextActionSubtitle = actionSubtitle,
                nextModuleIndex = nextUncompletedIndex
            )
        }
    }
}

/**
 * Primary Composable UI component that displays a rich progress bar and status tracking
 * for the user's progress through an active course.
 */
@Composable
fun CourseProgressTracker(
    course: Course,
    sections: List<CourseSection>,
    flashcards: List<Flashcard>,
    attempts: List<TestAttempt>,
    modifier: Modifier = Modifier,
    onNavigateToSection: ((Int) -> Unit)? = null,
    onNavigateToFlashcards: (() -> Unit)? = null,
    onNavigateToExam: (() -> Unit)? = null,
    onResumeNextAction: (() -> Unit)? = null
) {
    val progressState = remember(course, sections, flashcards, attempts) {
        CourseProgressState.fromCourseData(course, sections, flashcards, attempts)
    }

    CourseProgressTracker(
        state = progressState,
        sections = sections,
        modifier = modifier,
        onNavigateToSection = onNavigateToSection,
        onNavigateToFlashcards = onNavigateToFlashcards,
        onNavigateToExam = onNavigateToExam,
        onResumeNextAction = onResumeNextAction
    )
}

/**
 * Overload of CourseProgressTracker accepting a precomputed [CourseProgressState].
 */
@Composable
fun CourseProgressTracker(
    state: CourseProgressState,
    sections: List<CourseSection>,
    modifier: Modifier = Modifier,
    onNavigateToSection: ((Int) -> Unit)? = null,
    onNavigateToFlashcards: (() -> Unit)? = null,
    onNavigateToExam: (() -> Unit)? = null,
    onResumeNextAction: (() -> Unit)? = null
) {
    val animatedSectionProgress by animateFloatAsState(
        targetValue = state.sectionProgress,
        animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing),
        label = "animated_section_progress"
    )

    val animatedOverallProgress by animateFloatAsState(
        targetValue = state.overallProgress,
        animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing),
        label = "animated_overall_progress"
    )

    val phaseColor = Color(state.phase.accentColorHex)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("course_progress_tracker"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Top Row: Status Badge Pill & Overall Percentage
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status Badge Pill
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = phaseColor.copy(alpha = 0.14f),
                    border = BorderStroke(1.dp, phaseColor.copy(alpha = 0.35f)),
                    modifier = Modifier.testTag("progress_status_badge")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = state.phase.icon,
                            contentDescription = null,
                            tint = phaseColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = state.phase.title,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = phaseColor
                        )
                    }
                }

                // Overall Percentage Display
                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "${(animatedOverallProgress * 100).toInt()}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.testTag("progress_percentage_text")
                        )
                        Text(
                            text = "%",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 3.dp, start = 1.dp)
                        )
                    }
                    Text(
                        text = "Overall Mastery",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Primary Progress Bar Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Curriculum Completion",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${state.completedSections} of ${state.totalSections} Modules (${(state.sectionProgress * 100).toInt()}%)",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Primary Animated Linear Progress Bar
            LinearProgressIndicator(
                progress = { animatedSectionProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .testTag("progress_bar"),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            // Segmented Step Indicator (shown if <= 12 sections for clean visual scanning)
            if (sections.isNotEmpty() && sections.size <= 12) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    sections.forEachIndexed { index, section ->
                        val isCompleted = section.isCompleted
                        val isNext = index == state.nextModuleIndex
                        val segmentColor = when {
                            isCompleted -> MaterialTheme.colorScheme.primary
                            isNext -> MaterialTheme.colorScheme.secondary
                            else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(segmentColor)
                                .then(
                                    if (onNavigateToSection != null) {
                                        Modifier.clickable { onNavigateToSection(index) }
                                    } else Modifier
                                )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tri-metric Status Tracking Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Modules Status Pill
                StatusMetricPill(
                    icon = Icons.Default.School,
                    title = "Modules",
                    value = "${state.completedSections}/${state.totalSections}",
                    status = if (state.completedSections == state.totalSections) "Done" else "Active",
                    isDone = state.completedSections == state.totalSections,
                    modifier = Modifier.weight(1f)
                )

                // Flashcards Status Pill
                StatusMetricPill(
                    icon = Icons.Default.Style,
                    title = "Flashcards",
                    value = if (state.totalFlashcards > 0) "${state.masteredFlashcards}/${state.totalFlashcards}" else "None",
                    status = if (state.totalFlashcards > 0 && state.masteredFlashcards == state.totalFlashcards) "Mastered" else "${(state.flashcardProgress * 100).toInt()}%",
                    isDone = state.totalFlashcards > 0 && state.masteredFlashcards == state.totalFlashcards,
                    modifier = Modifier
                        .weight(1f)
                        .then(
                            if (onNavigateToFlashcards != null) {
                                Modifier.clickable { onNavigateToFlashcards() }
                            } else Modifier
                        )
                )

                // Exam Status Pill
                StatusMetricPill(
                    icon = Icons.Default.Quiz,
                    title = "Final Exam",
                    value = state.examBestScore?.let { "$it%" } ?: "Pending",
                    status = state.examBestScore?.let { GradeResult.calculate(it, 100).gradeLetter } ?: "Untaken",
                    isDone = state.examBestScore != null && state.examBestScore >= 70,
                    modifier = Modifier
                        .weight(1f)
                        .then(
                            if (onNavigateToExam != null) {
                                Modifier.clickable { onNavigateToExam() }
                            } else Modifier
                        )
                )
            }

            // Next Suggested Action Prompt Banner
            Spacer(modifier = Modifier.height(14.dp))
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "NEXT UP",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = state.nextActionLabel,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = state.nextActionSubtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (onResumeNextAction != null) {
                                onResumeNextAction()
                            } else if (state.nextModuleIndex != null && onNavigateToSection != null) {
                                onNavigateToSection(state.nextModuleIndex)
                            } else if (state.completedSections == state.totalSections && onNavigateToExam != null) {
                                onNavigateToExam()
                            } else if (onNavigateToFlashcards != null) {
                                onNavigateToFlashcards()
                            }
                        },
                        modifier = Modifier.testTag("next_action_button"),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(
                            horizontal = 14.dp,
                            vertical = 8.dp
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Continue",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Compact, reusable Progress Bar with status indicator label.
 */
@Composable
fun CourseProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    label: String? = null,
    showPercentage: Boolean = true,
    height: Dp = 8.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "compact_progress"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        if (label != null || showPercentage) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (label != null) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                if (showPercentage) {
                    Text(
                        text = "${(animatedProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = color
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(height / 2)),
            color = color,
            trackColor = trackColor
        )
    }
}

/**
 * Sub-metric tracking pill card for Modules, Flashcards, or Exam.
 */
@Composable
private fun StatusMetricPill(
    icon: ImageVector,
    title: String,
    value: String,
    status: String,
    isDone: Boolean,
    modifier: Modifier = Modifier
) {
    val doneColor = Color(0xFF2E7D32)

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
        border = BorderStroke(
            1.dp,
            if (isDone) doneColor.copy(alpha = 0.35f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        ),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isDone) doneColor else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                if (isDone) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = doneColor,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "$title • $status",
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp,
                color = if (isDone) doneColor else MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
