package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.GradeResult
import com.example.data.model.TestAttempt
import com.example.ui.components.ThemeToggleIconButton
import com.example.ui.theme.StudyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamResultScreen(
    course: Course,
    attempt: TestAttempt,
    onRetakeExam: () -> Unit,
    onReturnToCourse: () -> Unit,
    onViewCourseHistory: (() -> Unit)? = null,
    onViewDegreeCurricula: (() -> Unit)? = null
) {
    val gradeResult = GradeResult.calculate(attempt.correctCount, attempt.totalQuestions)
    val gradeColor = Color(gradeResult.colorHex)
    val feedbackColors = StudyTheme.feedbackColors
    val isPassing = attempt.scorePercentage >= 70

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exam Diagnostic Scorecard", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onReturnToCourse) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Return")
                    }
                },
                actions = {
                    onViewCourseHistory?.let { viewHistory ->
                        IconButton(onClick = viewHistory) {
                            Icon(Icons.Default.History, contentDescription = "Course History")
                        }
                    }
                    ThemeToggleIconButton()
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Main Scorecard Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("exam_scorecard_banner"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = gradeColor.copy(alpha = 0.12f)
                    ),
                    border = BorderStroke(2.dp, gradeColor.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Letter Grade Circular Badge
                        Surface(
                            shape = CircleShape,
                            color = gradeColor,
                            modifier = Modifier.size(84.dp),
                            shadowElevation = 4.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = gradeResult.gradeLetter,
                                    fontSize = 42.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "${attempt.scorePercentage}% Mastery Score",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = gradeColor
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${attempt.correctCount} of ${attempt.totalQuestions} Questions Answered Correctly",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Attempt #${attempt.attemptNumber} • Course Completion Preserved",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                        ) {
                            Text(
                                text = gradeResult.feedbackMessage,
                                modifier = Modifier.padding(14.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Action Buttons: Retest Flow (strictly optional) & Return
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onRetakeExam,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("retake_exam_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Replay, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Retake Exam", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onReturnToCourse,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("return_to_course_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Return to Course", fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Degree Curriculum & History Pathway Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isPassing) Color(0xFF2E7D32).copy(alpha = 0.08f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (isPassing) Color(0xFF2E7D32).copy(alpha = 0.3f) else MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isPassing) Icons.Default.WorkspacePremium else Icons.Default.School,
                                contentDescription = null,
                                tint = if (isPassing) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (isPassing) "Degree Requirement Fulfilled! 🎓" else "Degree Requirement Progress",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isPassing) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isPassing) {
                                "Score of ${attempt.scorePercentage}% satisfies the minimum 70% threshold required for academic degree programs and verified course certificates."
                            } else {
                                "Academic degree programs require a 70% passing threshold on final examinations. You can retake this diagnostic exam at any time to improve your standing."
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            onViewDegreeCurricula?.let { viewDegrees ->
                                OutlinedButton(
                                    onClick = viewDegrees,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Degree Pathways", fontSize = 12.sp)
                                }
                            }

                            onViewCourseHistory?.let { viewHistory ->
                                OutlinedButton(
                                    onClick = viewHistory,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Course History", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Diagnostic Question Breakdown Header
            item {
                Column {
                    Text(
                        text = "Diagnostic Question Breakdown",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Detailed conceptual analysis for each response to accelerate active recall and long-term retention.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Question feedback items
            itemsIndexed(attempt.questionFeedbacks) { idx, feedback ->
                val cardBorder = if (feedback.isCorrect) feedbackColors.success else feedbackColors.error
                val cardBg = if (feedback.isCorrect) {
                    feedbackColors.successContainer
                } else {
                    feedbackColors.errorContainer
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    border = BorderStroke(1.5.dp, cardBorder.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Question ${idx + 1}",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (feedback.isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                    contentDescription = null,
                                    tint = cardBorder,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (feedback.isCorrect) "Correct" else "Missed",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = cardBorder
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = feedback.questionText,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                if (!feedback.isCorrect) {
                                    Text(
                                        text = "Your Answer: ${feedback.selectedAnswerText}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFFC62828),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                                Text(
                                    text = "Accurate Answer: ${feedback.correctAnswerText}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF2E7D32),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Diagnostic rationale
                        Text(
                            text = "Diagnostic Rationale:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = feedback.diagnosticExplanation,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
