package com.example.ui.screens.dashboard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.ui.components.CourseThumbnail
import com.example.ui.theme.DashboardTokens

/**
 * Top Segmented Navigation Header Bar mimicking the reference:
 * [Settings | Account | Messages(5) | Favorites | Me v [Avatar]]
 */
@Composable
fun DashboardHeaderBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    activeAlertCount: Int = 5,
    onProfileClick: () -> Unit = {}
) {
    Surface(
        color = DashboardTokens.CardBackground,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, DashboardTokens.CardBorder, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                DashboardHeaderTab(
                    title = "Dashboard",
                    isSelected = selectedTab == "Dashboard",
                    onClick = { onTabSelected("Dashboard") }
                )
                DashboardHeaderTab(
                    title = "Courses",
                    isSelected = selectedTab == "Courses",
                    onClick = { onTabSelected("Courses") }
                )
                DashboardHeaderTab(
                    title = "Degrees",
                    isSelected = selectedTab == "Degrees",
                    onClick = { onTabSelected("Degrees") }
                )
                DashboardHeaderTab(
                    title = "Certs",
                    badgeCount = activeAlertCount,
                    isSelected = selectedTab == "Certs",
                    onClick = { onTabSelected("Certs") }
                )
                DashboardHeaderTab(
                    title = "History",
                    isSelected = selectedTab == "History",
                    onClick = { onTabSelected("History") }
                )
            }

            // User dropdown pill
            Surface(
                color = DashboardTokens.CardBackgroundElevated,
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.CardBorderLight),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clickable(onClick = onProfileClick)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Austin P.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DashboardTokens.TextWhite
                    )
                    Spacer(Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        DashboardTokens.EmeraldGreen,
                                        DashboardTokens.DarkGreenHeader
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "A",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardHeaderTab(
    title: String,
    isSelected: Boolean,
    badgeCount: Int? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) DashboardTokens.DarkGreenHeader else Color.Transparent
            )
            .border(
                1.dp,
                if (isSelected) DashboardTokens.EmeraldGreen else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) DashboardTokens.TextWhite else DashboardTokens.TextGrey
            )
            if (badgeCount != null && badgeCount > 0) {
                Spacer(Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(DashboardTokens.CrimsonRed),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$badgeCount",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

/**
 * 1. Learner Profile Card Widget (Center top)
 */
@Composable
fun LearnerProfileWidget(
    studentName: String = "Austin Phipps",
    degreeMajor: String = "B.S. in Computer Science & Trades",
    quizzesCompleted: Int = 23,
    studyHours: Int = 841,
    masteredDecks: Int = 49,
    onProfileClick: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DashboardTokens.CardBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.CardBorder),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(18.dp))

            // Avatar with decorative green ring
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(DashboardTokens.CardBackgroundElevated)
                    .border(2.dp, DashboardTokens.EmeraldGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.sweepGradient(
                                listOf(
                                    DashboardTokens.ForestGreen,
                                    DashboardTokens.EmeraldGreen,
                                    DashboardTokens.DarkGreenHeader
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "Avatar",
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = studentName,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = DashboardTokens.TextWhite
            )

            Text(
                text = degreeMajor,
                fontSize = 12.sp,
                color = DashboardTokens.TextGrey,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = DashboardTokens.CardBorder)

            // Bottom social-style stats bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DashboardTokens.CardBackgroundElevated)
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileStatPill(
                    icon = Icons.Default.Assessment,
                    count = quizzesCompleted.toString(),
                    tint = DashboardTokens.EmeraldGreen
                )
                ProfileStatPill(
                    icon = Icons.Default.Visibility,
                    count = "${studyHours}h",
                    tint = DashboardTokens.TextGrey
                )
                ProfileStatPill(
                    icon = Icons.Default.Favorite,
                    count = masteredDecks.toString(),
                    tint = DashboardTokens.CrimsonRed
                )
            }
        }
    }
}

@Composable
private fun ProfileStatPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    count: String,
    tint: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = count,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = DashboardTokens.TextWhite
        )
    }
}

/**
 * 2. Menu Box Widget (Left top) with Dark Green Header
 */
@Composable
fun MenuBoxWidget(
    courseCount: Int,
    certCount: Int,
    degreeCount: Int,
    onCourseListClick: () -> Unit,
    onDegreeCurriculaClick: () -> Unit,
    onCertificationsClick: () -> Unit,
    onHistoryClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DashboardTokens.CardBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.CardBorder),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Dark Green header banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(DashboardTokens.DarkGreenHeader, DashboardTokens.ForestGreen)
                        )
                    )
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "ACADEMY MENU",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = DashboardTokens.TextWhite,
                    letterSpacing = 1.sp
                )
            }

            // Menu rows
            MenuBoxRow(
                icon = Icons.AutoMirrored.Filled.MenuBook,
                title = "Enrolled Courses",
                badgeText = courseCount.toString(),
                badgeColor = DashboardTokens.CardBackgroundElevated,
                badgeTextColor = DashboardTokens.TextWhite,
                onClick = onCourseListClick
            )
            HorizontalDivider(color = DashboardTokens.CardBorder)

            MenuBoxRow(
                icon = Icons.Default.School,
                title = "Degree Tracks",
                badgeText = "$degreeCount Ready",
                badgeColor = DashboardTokens.CrimsonRed,
                badgeTextColor = Color.White,
                onClick = onDegreeCurriculaClick
            )
            HorizontalDivider(color = DashboardTokens.CardBorder)

            MenuBoxRow(
                icon = Icons.Default.Star,
                title = "Free Certifications",
                badgeText = certCount.toString(),
                badgeColor = DashboardTokens.ForestGreen,
                badgeTextColor = DashboardTokens.EmeraldGreen,
                onClick = onCertificationsClick
            )
            HorizontalDivider(color = DashboardTokens.CardBorder)

            MenuBoxRow(
                icon = Icons.Default.Assessment,
                title = "Exam Scoring & History",
                badgeText = "Live",
                badgeColor = DashboardTokens.CardBackgroundElevated,
                badgeTextColor = DashboardTokens.TextGrey,
                onClick = onHistoryClick
            )
        }
    }
}

@Composable
private fun MenuBoxRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    badgeText: String,
    badgeColor: Color,
    badgeTextColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = DashboardTokens.TextGrey,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = DashboardTokens.TextWhite
            )
        }

        Surface(
            color = badgeColor,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = badgeText,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = badgeTextColor,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
        }
    }
}

/**
 * 3. Curriculum Distribution Donut Chart Widget ("OS AUDIENCE STATS")
 */
@Composable
fun CurriculumDonutWidget(
    csPct: Float = 0.35f,
    mathPct: Float = 0.25f,
    tradesPct: Float = 0.20f,
    sciPct: Float = 0.20f
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DashboardTokens.CardBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.CardBorder),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CURRICULUM DISTRIBUTION",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = DashboardTokens.TextWhite,
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(16.dp))

            // Canvas Donut Chart
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 18.dp.toPx()
                    val diameter = size.minDimension - strokeWidth
                    val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
                    val arcSize = Size(diameter, diameter)

                    var startAngle = -90f

                    val slices = listOf(
                        csPct to DashboardTokens.SegmentGreen,
                        mathPct to DashboardTokens.SegmentTeal,
                        tradesPct to DashboardTokens.SegmentRed,
                        sciPct to DashboardTokens.SegmentDarkGreen
                    )

                    for ((pct, color) in slices) {
                        val sweep = pct * 360f
                        drawArc(
                            color = color,
                            startAngle = startAngle,
                            sweepAngle = sweep - 3f, // Small gap
                            useCenter = false,
                            topLeft = topLeft,
                            size = arcSize,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                        startAngle += sweep
                    }
                }

                // Inner label
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "ACADEMY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DashboardTokens.TextWhite
                    )
                    Text(
                        text = "2026",
                        fontSize = 10.sp,
                        color = DashboardTokens.TextGrey
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = DashboardTokens.CardBorder)

            // Segmented bottom metrics row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DashboardTokens.CardBackgroundElevated)
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DonutLegendCol(label = "CS", pct = "35%", color = DashboardTokens.SegmentGreen)
                DonutLegendCol(label = "Math", pct = "25%", color = DashboardTokens.SegmentTeal)
                DonutLegendCol(label = "Trades", pct = "20%", color = DashboardTokens.SegmentRed)
                DonutLegendCol(label = "Science", pct = "20%", color = DashboardTokens.SegmentDarkGreen)
            }
        }
    }
}

@Composable
private fun DonutLegendCol(label: String, pct: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(20.dp)
                .height(3.dp)
                .background(color, RoundedCornerShape(2.dp))
        )
        Spacer(Modifier.height(4.dp))
        Text(text = label, fontSize = 11.sp, color = DashboardTokens.TextGrey)
        Text(
            text = pct,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = DashboardTokens.TextWhite
        )
    }
}

/**
 * 4. Academic Trend Line Chart Widget
 */
@Composable
fun AcademicTrendChartWidget() {
    var selectedPeriod by remember { mutableStateOf("Month") }

    Card(
        colors = CardDefaults.cardColors(containerColor = DashboardTokens.CardBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.CardBorder),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header with percentage badge
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MASTERY PROGRESSION",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = DashboardTokens.TextWhite,
                    letterSpacing = 0.5.sp
                )

                Surface(
                    color = DashboardTokens.CrimsonRed,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "+28%",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            // Canvas Line Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(horizontal = 14.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val points = listOf(
                        Offset(0f, h * 0.75f),
                        Offset(w * 0.25f, h * 0.65f),
                        Offset(w * 0.5f, h * 0.8f),
                        Offset(w * 0.75f, h * 0.25f),
                        Offset(w, h * 0.4f)
                    )

                    // Draw gridlines
                    for (i in 1..3) {
                        val y = h * (i / 4f)
                        drawLine(
                            color = DashboardTokens.CardBorder,
                            start = Offset(0f, y),
                            end = Offset(w, y),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    // Draw filled area under curve
                    val areaPath = Path().apply {
                        moveTo(points.first().x, h)
                        points.forEach { lineTo(it.x, it.y) }
                        lineTo(points.last().x, h)
                        close()
                    }
                    drawPath(
                        path = areaPath,
                        brush = Brush.verticalGradient(
                            listOf(
                                DashboardTokens.EmeraldGreen.copy(alpha = 0.25f),
                                Color.Transparent
                            )
                        )
                    )

                    // Draw line
                    val linePath = Path().apply {
                        moveTo(points.first().x, points.first().y)
                        for (i in 1 until points.size) {
                            lineTo(points[i].x, points[i].y)
                        }
                    }
                    drawPath(
                        path = linePath,
                        color = DashboardTokens.EmeraldGreen,
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // Draw peak dot nodes
                    points.forEach { pt ->
                        drawCircle(
                            color = DashboardTokens.CardBackground,
                            radius = 5.dp.toPx(),
                            center = pt
                        )
                        drawCircle(
                            color = DashboardTokens.MintGreen,
                            radius = 3.dp.toPx(),
                            center = pt
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Time filter pills
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                listOf("Week", "Month", "Year").forEach { period ->
                    val isChosen = period == selectedPeriod
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (isChosen) DashboardTokens.CrimsonRed else DashboardTokens.CardBackgroundElevated
                            )
                            .clickable { selectedPeriod = period }
                            .padding(horizontal = 14.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = period,
                            fontSize = 11.sp,
                            fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Normal,
                            color = if (isChosen) Color.White else DashboardTokens.TextGrey
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))
            HorizontalDivider(color = DashboardTokens.CardBorder)

            // Monthly deltas
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DashboardTokens.CardBackgroundElevated)
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                DeltaRow(label = "APR 2026", delta = "+21%")
                DeltaRow(label = "MAY 2026", delta = "+48%")
                DeltaRow(label = "JUN 2026", delta = "+35%")
            }
        }
    }
}

@Composable
private fun DeltaRow(label: String, delta: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 11.sp, color = DashboardTokens.TextGrey)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .clip(CircleShape)
                    .background(DashboardTokens.CrimsonRed)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = delta,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = DashboardTokens.TextWhite
            )
        }
    }
}

/**
 * 5. Study Streak & Schedule Widget (mimics the weather card)
 * Uses Red as the header and Dark Grey / Black base
 */
@Composable
fun StudyStreakCardWidget() {
    Card(
        colors = CardDefaults.cardColors(containerColor = DashboardTokens.CardBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.CardBorder),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Crimson Red top banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(DashboardTokens.DarkRedHeader, DashboardTokens.CrimsonRed)
                        )
                    )
                    .padding(14.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "CAMPUS / STUDY STREAK",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.9f),
                            letterSpacing = 1.sp
                        )
                        Icon(
                            imageVector = Icons.Default.Whatshot,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = "FRI 26/09",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "24°",
                                fontSize = 34.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Days Active",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Whatshot,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.85f),
                            modifier = Modifier.size(52.dp)
                        )
                    }
                }
            }

            // 6-Day Study Forecast Rows
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                StudyForecastRow("SAT 27/09", "Linear Algebra", "25°", isHighlight = false)
                StudyForecastRow("SUN 28/09", "CRISPR Genetics", "22°", isHighlight = true)
                StudyForecastRow("MON 29/09", "HVAC 608 Vacuum", "24°", isHighlight = false)
                StudyForecastRow("TUE 30/09", "Distributed Sync", "26°", isHighlight = false)
                StudyForecastRow("WED 01/10", "Millwright LOTO", "27°", isHighlight = false)
            }
        }
    }
}

@Composable
private fun StudyForecastRow(
    date: String,
    subject: String,
    metric: String,
    isHighlight: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = date,
            fontSize = 11.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Normal,
            color = if (isHighlight) DashboardTokens.CrimsonRed else DashboardTokens.TextGrey
        )
        Text(
            text = subject,
            fontSize = 11.sp,
            color = DashboardTokens.TextWhite,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f, fill = false).padding(horizontal = 8.dp)
        )
        Text(
            text = metric,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = DashboardTokens.TextWhite
        )
    }
}

/**
 * 6. Date & Calendar Widget
 */
@Composable
fun DayAndCalendarWidget(onScheduleExam: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Left: Big Day Card
        Card(
            colors = CardDefaults.cardColors(containerColor = DashboardTokens.CardBackground),
            border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.CardBorder),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DashboardTokens.ForestGreen)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "WEDNESDAY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DashboardTokens.TextWhite,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(Modifier.height(14.dp))

                Text(
                    text = "26",
                    fontSize = 44.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DashboardTokens.TextWhite
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = onScheduleExam,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DashboardTokens.EmeraldGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "SCHEDULE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Right: Mini Month Calendar
        Card(
            colors = CardDefaults.cardColors(containerColor = DashboardTokens.CardBackground),
            border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.CardBorder),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1.3f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Month Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "<",
                        fontSize = 12.sp,
                        color = DashboardTokens.TextGrey,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "APRIL 2026",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DashboardTokens.TextWhite
                    )
                    Text(
                        text = ">",
                        fontSize = 12.sp,
                        color = DashboardTokens.TextGrey,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(6.dp))

                // Days of week
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    listOf("S", "M", "T", "W", "R", "F", "S").forEach { day ->
                        Text(
                            text = day,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DashboardTokens.TextGrey
                        )
                    }
                }

                Spacer(Modifier.height(6.dp))

                // Calendar Numbers Grid (Simulated with highlight on 26)
                val rows = listOf(
                    listOf("2", "3", "4", "5", "6", "7", "8"),
                    listOf("9", "10", "11", "12", "13", "14", "15"),
                    listOf("16", "17", "18", "19", "20", "21", "22"),
                    listOf("23", "24", "25", "26", "27", "28", "29")
                )

                rows.forEach { week ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        week.forEach { num ->
                            if (num == "26") {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(DashboardTokens.CrimsonRed),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = num,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            } else {
                                Box(
                                    modifier = Modifier.size(20.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = num,
                                        fontSize = 10.sp,
                                        color = DashboardTokens.TextGrey
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 7. Progress / Sync Bars Widget
 */
@Composable
fun ResourceSyncBarsWidget(
    courseDownloadPct: Float = 0.81f,
    flashcardSyncPct: Float = 0.43f
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DashboardTokens.CardBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.CardBorder),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // First bar
            SyncProgressBar(
                icon = Icons.Default.CloudDownload,
                label = "Offline Course Cache...",
                pct = courseDownloadPct,
                barColor = DashboardTokens.EmeraldGreen
            )

            Spacer(Modifier.height(14.dp))

            // Second bar
            SyncProgressBar(
                icon = Icons.Default.CloudUpload,
                label = "Active Recall Mastered...",
                pct = flashcardSyncPct,
                barColor = DashboardTokens.SegmentTeal
            )
        }
    }
}

@Composable
private fun SyncProgressBar(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    pct: Float,
    barColor: Color
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Track
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(DashboardTokens.CardBackgroundElevated)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(pct)
                    .clip(RoundedCornerShape(4.dp))
                    .background(barColor)
            )
        }

        Spacer(Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = DashboardTokens.TextGrey,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = DashboardTokens.TextGrey
                )
            }
            Text(
                text = "${(pct * 100).toInt()}%",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = DashboardTokens.TextWhite
            )
        }
    }
}

/**
 * 8. Academic Feed / Wire Widget (mimicking latest updates card)
 */
@Composable
fun AcademicFeedWidget() {
    Card(
        colors = CardDefaults.cardColors(containerColor = DashboardTokens.CardBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.CardBorder),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Dark Green header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DashboardTokens.DarkGreenHeader)
                    .padding(vertical = 10.dp, horizontal = 14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = DashboardTokens.EmeraldGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "ACADEMY ACCREDITATIONS & WIRE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DashboardTokens.TextWhite,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // Feed Items
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Text(
                    text = "Harvard CS50x curriculum problem sets accredited for B.S. Computer Science degree capstone transfer.",
                    fontSize = 12.sp,
                    color = DashboardTokens.TextWhite,
                    lineHeight = 16.sp
                )
                Text(
                    text = "3 minutes ago • OpenLearn Consortium",
                    fontSize = 10.sp,
                    color = DashboardTokens.TextGrey,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(Modifier.height(10.dp))
                HorizontalDivider(color = DashboardTokens.CardBorder)
                Spacer(Modifier.height(10.dp))

                Text(
                    text = "EPA Section 608 Universal testing guidelines updated with 2026 low-GWP HFO refrigerant recovery standards.",
                    fontSize = 12.sp,
                    color = DashboardTokens.TextWhite,
                    lineHeight = 16.sp
                )
                Text(
                    text = "6 hours ago • Trades Verification Board",
                    fontSize = 10.sp,
                    color = DashboardTokens.TextGrey,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

/**
 * 9. Quick Enrollment / Course Generator Form Widget
 */
@Composable
fun QuickEnrollWidget(
    onGenerateCourse: (String) -> Unit
) {
    var topicQuery by remember { mutableStateOf("") }

    Card(
        colors = CardDefaults.cardColors(containerColor = DashboardTokens.CardBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.CardBorder),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Text(
                text = "RAPID SYLLABUS GENERATOR",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = DashboardTokens.TextWhite,
                letterSpacing = 0.5.sp
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = topicQuery,
                onValueChange = { topicQuery = it },
                placeholder = {
                    Text(
                        "e.g. Quantum Computing, HVAC Heat Pumps",
                        fontSize = 12.sp,
                        color = DashboardTokens.TextMuted
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = DashboardTokens.TextGrey,
                        modifier = Modifier.size(18.dp)
                    )
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = DashboardTokens.CardBackgroundElevated,
                    unfocusedContainerColor = DashboardTokens.CardBackgroundElevated,
                    focusedBorderColor = DashboardTokens.EmeraldGreen,
                    unfocusedBorderColor = DashboardTokens.CardBorder,
                    focusedTextColor = DashboardTokens.TextWhite,
                    unfocusedTextColor = DashboardTokens.TextWhite
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quick_topic_input")
            )

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = {
                    if (topicQuery.isNotBlank()) {
                        onGenerateCourse(topicQuery.trim())
                    }
                },
                enabled = topicQuery.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DashboardTokens.CrimsonRed,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quick_generate_button")
            ) {
                Text(
                    text = "GENERATE FULL SYLLABUS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * 10. Lecture Media Player Preview Widget (Bottom left in image)
 */
@Composable
fun LectureMediaPreviewWidget(
    course: Course? = null,
    onPlayCourse: (() -> Unit)? = null
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DashboardTokens.CardBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.CardBorder),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onPlayCourse != null) { onPlayCourse?.invoke() }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Video Thumbnail Simulation or Course Thumbnail
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                contentAlignment = Alignment.Center
            ) {
                if (course != null) {
                    CourseThumbnail(
                        course = course,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        DashboardTokens.CardBackgroundElevated,
                                        DashboardTokens.DarkGreenHeader
                                    )
                                )
                            )
                    )
                }

                // Play button overlay
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.65f))
                        .border(1.5.dp, DashboardTokens.EmeraldGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play Lecture",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // Bottom badge
                Surface(
                    color = Color.Black.copy(alpha = 0.75f),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                ) {
                    Text(
                        text = course?.let { "▶ ${it.title}" } ?: "Masterclass: Lockout/Tagout Isolation",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Scrubber Bar & Controls
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DashboardTokens.CardBackgroundElevated)
                    .padding(8.dp)
            ) {
                // Scrubber line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(DashboardTokens.CardBorder)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.45f)
                            .background(DashboardTokens.CrimsonRed)
                    )
                }

                Spacer(Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "4:15 / 9:23",
                        fontSize = 10.sp,
                        color = DashboardTokens.TextGrey,
                        fontWeight = FontWeight.Medium
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = null,
                            tint = DashboardTokens.TextGrey,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * 11. Action Buttons & Metrics Footer Widget (Bottom in image)
 */
@Composable
fun QuickActionFooterWidget(
    onFlashcardRecallClick: () -> Unit,
    onDegreeAuditsClick: () -> Unit,
    onFreeCertsClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Stacked colored buttons
        Button(
            onClick = onFlashcardRecallClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = DashboardTokens.ForestGreen,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = "START ACTIVE RECALL PRACTICE",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = onDegreeAuditsClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = DashboardTokens.DarkGreenHeader,
                contentColor = DashboardTokens.EmeraldGreen
            ),
            shape = RoundedCornerShape(8.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.EmeraldGreen),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = "AUDIT DEGREE CURRICULUM",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = onFreeCertsClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = DashboardTokens.CrimsonRed,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = "EXPLORE 15+ FREE CERTIFICATIONS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(10.dp))

        // Horizontal metric counts bar
        Surface(
            color = DashboardTokens.CardBackground,
            shape = RoundedCornerShape(8.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DashboardTokens.CardBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MetricCounterCol("COURSES", "18", DashboardTokens.EmeraldGreen)
                MetricCounterCol("FLASHCARDS", "120+", DashboardTokens.MintGreen)
                MetricCounterCol("QUIZZES", "65", DashboardTokens.TextWhite)
                MetricCounterCol("DEGREES", "6", DashboardTokens.CrimsonRed)
            }
        }
    }
}

@Composable
private fun MetricCounterCol(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 9.sp,
            color = DashboardTokens.TextGrey,
            fontWeight = FontWeight.SemiBold
        )
    }
}
