package com.example.ui.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.CourseSection
import com.example.data.model.Flashcard
import com.example.data.model.ScopeTier
import com.example.data.model.TestAttempt
import com.example.ui.UiScreen
import com.example.ui.components.CourseCertificationPathwayCard
import com.example.ui.components.CourseProgressTracker
import com.example.ui.components.CourseThumbnail
import com.example.ui.components.FreeCertificationsSheet
import com.example.ui.components.ThemeToggleIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    course: Course,
    sections: List<CourseSection>,
    flashcards: List<Flashcard>,
    attempts: List<TestAttempt>,
    onBack: () -> Unit,
    onNavigate: (UiScreen) -> Unit,
    onExportCourse: () -> Unit,
    onOpenAttemptsDialog: () -> Unit
) {
    var showCertificationsSheet by remember { mutableStateOf(false) }

    val completedSections = sections.count { it.isCompleted }
    val totalSections = sections.size.coerceAtLeast(1)
    val progress = (completedSections.toFloat() / totalSections.toFloat()).coerceIn(0f, 1f)

    val masteredCards = flashcards.count { it.isMastered }

    val tierColor = when (course.tier) {
        ScopeTier.SHORT -> Color(0xFF2E7D32)
        ScopeTier.MEDIUM -> Color(0xFF1565C0)
        ScopeTier.LONG -> Color(0xFF6A1B9A)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = course.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    ThemeToggleIconButton()
                    IconButton(
                        onClick = { onNavigate(UiScreen.CourseHistory) },
                        modifier = Modifier.testTag("open_history_from_detail")
                    ) {
                        Icon(Icons.Default.History, contentDescription = "Course History")
                    }
                    IconButton(
                        onClick = { showCertificationsSheet = true },
                        modifier = Modifier.testTag("open_certifications_from_detail")
                    ) {
                        Icon(Icons.Default.School, contentDescription = "Certifications Hub")
                    }
                    IconButton(onClick = onExportCourse) {
                        Icon(Icons.Default.Share, contentDescription = "Export Course")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Course Hero Thumbnail
            item {
                CourseThumbnail(
                    course = course,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(18.dp))
                )
            }

            // Header summary card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    )
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = tierColor.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "${course.proficiencyLevel.emoji} ${course.proficiencyLevel.title} • ${course.duration.title} • ${sections.size} Modules (~${course.duration.estimatedTime})",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = tierColor
                                )
                            }

                            if (course.bestScore != null) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "Best Exam: ${course.bestScore}%",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = course.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = course.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        if (course.certification != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.WorkspacePremium,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Official Free Credential: ${course.certification.title}",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.primary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Real-World Free Certification Pathway Card
            if (course.certification != null) {
                item {
                    CourseCertificationPathwayCard(
                        certification = course.certification,
                        onViewAllCertifications = { showCertificationsSheet = true }
                    )
                }
            }

            // Active Course Progress & Status Tracker
            item {
                CourseProgressTracker(
                    course = course,
                    sections = sections,
                    flashcards = flashcards,
                    attempts = attempts,
                    onNavigateToSection = { sectionIndex ->
                        onNavigate(UiScreen.SectionReading(course.id, sectionIndex))
                    },
                    onNavigateToFlashcards = {
                        onNavigate(UiScreen.FlashcardDeck(course.id))
                    },
                    onNavigateToExam = {
                        onNavigate(UiScreen.Exam(course.id))
                    },
                    onResumeNextAction = {
                        val nextUncompleted = sections.indexOfFirst { !it.isCompleted }.takeIf { it >= 0 }
                        if (nextUncompleted != null) {
                            onNavigate(UiScreen.SectionReading(course.id, nextUncompleted))
                        } else if (attempts.isEmpty()) {
                            onNavigate(UiScreen.Exam(course.id))
                        } else {
                            onNavigate(UiScreen.FlashcardDeck(course.id))
                        }
                    }
                )
            }

            // Study & Evaluation Center
            item {
                Text(
                    text = "Study & Evaluation Systems",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Flashcards Card
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigate(UiScreen.FlashcardDeck(course.id)) }
                            .testTag("open_flashcards_button"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Icon(
                                imageVector = Icons.Default.Style,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Flashcard Deck",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$masteredCards/${flashcards.size} Mastered",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Interactive 3D drill with self-assessment status.",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Final Exam Card
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigate(UiScreen.Exam(course.id)) }
                            .testTag("open_exam_button"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Icon(
                                imageVector = Icons.Default.Quiz,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Final Exam",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (attempts.isEmpty()) "Not attempted yet" else "${attempts.size} attempt(s) taken",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "A-F grade scale with diagnostic error feedback.",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            if (attempts.isNotEmpty()) {
                item {
                    OutlinedButton(
                        onClick = onOpenAttemptsDialog,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.History, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("View Score History (${attempts.size} Attempts)")
                    }
                }
            }

            // Section Curriculum List
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Curriculum Modules",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tap to read & review",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            itemsIndexed(sections, key = { _, sec -> sec.id }) { index, section ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onNavigate(UiScreen.SectionReading(course.id, index))
                        }
                        .testTag("section_item_$index"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (section.isCompleted) {
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (section.isCompleted) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                if (section.isCompleted) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Completed",
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                } else {
                                    Text(
                                        text = "${index + 1}",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = section.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Includes 3 key takeaways & active recall checkpoint",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Open Section",
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }

        if (showCertificationsSheet) {
            FreeCertificationsSheet(
                onDismiss = { showCertificationsSheet = false },
                onSelectCourse = { selectedCourseId ->
                    if (selectedCourseId != course.id) {
                        onNavigate(UiScreen.CourseDetail(selectedCourseId))
                    }
                }
            )
        }
    }
}
