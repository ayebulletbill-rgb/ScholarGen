package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.DegreeAcademicYear
import com.example.data.model.DegreeCourseStatus
import com.example.data.model.DegreeProgress
import com.example.ui.components.DegreeDiplomaDialog
import com.example.ui.components.ThemeToggleIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DegreeCurriculaScreen(
    degreeProgresses: List<DegreeProgress>,
    allCourses: List<Course>,
    onSelectCourse: (courseId: String) -> Unit,
    onBack: () -> Unit,
    onGenerateCourse: ((topic: String) -> Unit)? = null
) {
    var selectedFilter by remember { mutableStateOf("ALL") } // "ALL", "CONFERRED", "IN_PROGRESS"
    var viewingDiplomaFor by remember { mutableStateOf<DegreeProgress?>(null) }

    val filteredProgresses = remember(degreeProgresses, selectedFilter) {
        when (selectedFilter) {
            "CONFERRED" -> degreeProgresses.filter { it.isConferred }
            "IN_PROGRESS" -> degreeProgresses.filter { !it.isConferred && it.completedCount > 0 }
            else -> degreeProgresses
        }
    }

    val totalConferred = degreeProgresses.count { it.isConferred }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Collegiate Degree Curricula", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(
                            text = "Accredited 40-Course Brick & Mortar Programs (120 Credits)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    ThemeToggleIconButton()
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
            // Summary Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🏛️", fontSize = 28.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "University Degree Programs",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Full 4-Year Academic Undergraduate & Vocational Curricula",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "$totalConferred",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Black,
                                        color = if (totalConferred > 0) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Degrees Conferred",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "${degreeProgresses.size}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Black
                                    )
                                    Text(
                                        text = "Collegiate Majors",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val totalCourses = degreeProgresses.sumOf { it.totalCount }
                                    Text(
                                        text = "$totalCourses",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "Catalog Courses",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Filter Chips
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = selectedFilter == "ALL",
                        onClick = { selectedFilter = "ALL" },
                        label = { Text("All Majors (${degreeProgresses.size})") }
                    )
                    FilterChip(
                        selected = selectedFilter == "CONFERRED",
                        onClick = { selectedFilter = "CONFERRED" },
                        label = { Text("Conferred ($totalConferred)") }
                    )
                    FilterChip(
                        selected = selectedFilter == "IN_PROGRESS",
                        onClick = { selectedFilter = "IN_PROGRESS" },
                        label = { Text("In Progress") }
                    )
                }
            }

            // Degree Program Cards
            items(filteredProgresses, key = { it.program.id }) { progress ->
                DegreeProgramCard(
                    progress = progress,
                    onOpenDiploma = { viewingDiplomaFor = progress },
                    onSelectCourse = onSelectCourse,
                    onGenerateCourse = onGenerateCourse
                )
            }
        }
    }

    // Diploma Viewer Dialog
    viewingDiplomaFor?.let { progress ->
        DegreeDiplomaDialog(
            progress = progress,
            studentName = "Austin Phipps",
            onDismiss = { viewingDiplomaFor = null }
        )
    }
}

@Composable
fun DegreeProgramCard(
    progress: DegreeProgress,
    onOpenDiploma: () -> Unit,
    onSelectCourse: (courseId: String) -> Unit,
    onGenerateCourse: ((topic: String) -> Unit)? = null
) {
    val program = progress.program
    var expanded by remember { mutableStateOf(false) }
    val levelColor = Color(program.level.colorHex)

    // Tracks which academic years are expanded inside the card
    val expandedYears = remember { mutableStateMapOf<String, Boolean>() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("degree_card_${program.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = if (progress.isConferred) 2.dp else 1.dp,
            color = if (progress.isConferred) Color(0xFFD4AF37) else MaterialTheme.colorScheme.outlineVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (progress.isConferred) 4.dp else 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Header Row: Emoji, Level Badge, Conferred Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = program.iconEmoji, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = levelColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "${program.level.title.uppercase()} • ${program.totalCredits} CREDITS",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = levelColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                if (progress.isConferred) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFD4AF37).copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, Color(0xFFD4AF37))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.WorkspacePremium,
                                contentDescription = null,
                                tint = Color(0xFFB8860B),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "CONFERRED",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFB8860B)
                            )
                        }
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "${progress.completedCount}/${progress.totalCount} Courses Passed",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Title & Department
            Text(
                text = program.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = program.department,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = program.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Progress Bar
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Curriculum Completion (${progress.completedCount}/${progress.totalCount} Courses • ${program.totalCredits} Credit Hours)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${(progress.progressPercentage * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (progress.isConferred) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { progress.progressPercentage },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = if (progress.isConferred) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Conferred Diploma Button
            if (progress.isConferred) {
                Button(
                    onClick = onOpenDiploma,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("view_diploma_button_${program.id}"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB8860B)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "View Conferred Diploma 🎓",
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Expand / Collapse Courses Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expanded) "Hide Collegiate Curriculum" else "View Full ${progress.totalCount}-Course Collegiate Curriculum (${program.academicYears.size} Academic Tiers)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // Expandable Course Status List grouped by Academic Year
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(modifier = Modifier.height(4.dp))

                    if (program.academicYears.isNotEmpty()) {
                        program.academicYears.forEach { academicYear ->
                            val yearStatuses = progress.getStatusesForYear(academicYear)
                            val yearPassedCount = yearStatuses.count { it.isPassed }
                            val yearTotalCount = yearStatuses.size
                            val isYearOpen = expandedYears[academicYear.yearName] ?: true

                            AcademicYearSection(
                                year = academicYear,
                                passedCount = yearPassedCount,
                                totalCount = yearTotalCount,
                                isOpen = isYearOpen,
                                onToggle = {
                                    expandedYears[academicYear.yearName] = !isYearOpen
                                }
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    yearStatuses.forEach { status ->
                                        DegreeCourseStatusItem(
                                            status = status,
                                            passingThreshold = program.passingScoreThreshold,
                                            onSelectCourse = {
                                                status.matchedCourse?.id?.let(onSelectCourse)
                                            },
                                            onEnrollCourse = {
                                                onGenerateCourse?.invoke(status.requiredTitle)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        progress.courseStatuses.forEach { status ->
                            DegreeCourseStatusItem(
                                status = status,
                                passingThreshold = program.passingScoreThreshold,
                                onSelectCourse = {
                                    status.matchedCourse?.id?.let(onSelectCourse)
                                },
                                onEnrollCourse = {
                                    onGenerateCourse?.invoke(status.requiredTitle)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AcademicYearSection(
    year: DegreeAcademicYear,
    passedCount: Int,
    totalCount: Int,
    isOpen: Boolean,
    onToggle: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (passedCount == totalCount && totalCount > 0) Icons.Default.CheckCircle else Icons.Default.School,
                        contentDescription = null,
                        tint = if (passedCount == totalCount && totalCount > 0) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = year.yearName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${year.semesterCredits} Credits • $passedCount / $totalCount Courses Passed",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Icon(
                    imageVector = if (isOpen) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(visible = isOpen) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    content()
                }
            }
        }
    }
}

@Composable
fun DegreeCourseStatusItem(
    status: DegreeCourseStatus,
    passingThreshold: Int,
    onSelectCourse: () -> Unit,
    onEnrollCourse: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = BorderStroke(
            width = 1.dp,
            color = if (status.isPassed) Color(0xFF2E7D32).copy(alpha = 0.4f) else MaterialTheme.colorScheme.outlineVariant
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = when {
                        status.isPassed -> Icons.Default.CheckCircle
                        status.matchedCourse != null -> Icons.Default.HourglassEmpty
                        else -> Icons.Default.PlayArrow
                    },
                    contentDescription = null,
                    tint = when {
                        status.isPassed -> Color(0xFF2E7D32)
                        status.matchedCourse != null -> Color(0xFFF57C00)
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = status.requiredTitle,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = when {
                            status.isPassed -> "Passed with ${status.bestScore}% (Threshold: ${passingThreshold}%)"
                            status.bestScore != null -> "Exam Score: ${status.bestScore}% (Requires ${passingThreshold}% to pass)"
                            status.matchedCourse != null -> "Enrolled • In Progress"
                            else -> "University Catalog Course • 3 Credits"
                        },
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 11.sp,
                        color = when {
                            status.isPassed -> Color(0xFF2E7D32)
                            status.bestScore != null -> Color(0xFFD32F2F)
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            if (status.matchedCourse != null) {
                OutlinedButton(
                    onClick = onSelectCourse,
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(if (status.isPassed) "Review" else "Study", fontSize = 11.sp)
                }
            } else {
                OutlinedButton(
                    onClick = onEnrollCourse,
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircleOutline,
                        contentDescription = null,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text("Enroll", fontSize = 11.sp)
                }
            }
        }
    }
}
