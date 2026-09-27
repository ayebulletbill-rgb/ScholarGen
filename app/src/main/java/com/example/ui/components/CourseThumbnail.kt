package com.example.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Path as AndroidPath
import android.graphics.RectF
import android.graphics.LinearGradient as AndroidLinearGradient
import android.graphics.RadialGradient as AndroidRadialGradient
import android.graphics.Shader as AndroidShader
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.model.Course
import com.example.data.model.EducationalCategory
import java.io.File
import java.io.FileOutputStream
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object CourseThumbnailGenerator {

    /**
     * Generates a high-resolution, thematic 16:9 (800x450) thumbnail image
     * tailored specifically to the course title and educational discipline.
     * Saves the resulting PNG to the app's internal files directory and returns its file URI.
     */
    fun generateAndSaveThumbnail(
        context: Context,
        courseId: String,
        title: String,
        topic: String,
        category: EducationalCategory
    ): String {
        return try {
            val dir = File(context.filesDir, "course_thumbnails").apply { mkdirs() }
            val file = File(dir, "thumb_${courseId.replace(Regex("[^a-zA-Z0-9_]"), "_")}.png")

            val width = 800
            val height = 450
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(bitmap)

            // 1. Draw rich dark base gradient
            val basePaint = Paint().apply {
                isAntiAlias = true
                shader = AndroidLinearGradient(
                    0f, 0f, width.toFloat(), height.toFloat(),
                    intArrayOf(
                        0xFF0B0E14.toInt(),
                        0xFF121820.toInt(),
                        0xFF1A222C.toInt(),
                        0xFF0E1319.toInt()
                    ),
                    floatArrayOf(0f, 0.4f, 0.8f, 1f),
                    AndroidShader.TileMode.CLAMP
                )
            }
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), basePaint)

            // Category accent colors
            val (accentColor, secondaryColor) = when (category) {
                EducationalCategory.COMPUTER_SCIENCE -> 0xFF10B981.toInt() to 0xFF06B6D4.toInt() // Emerald to Cyan
                EducationalCategory.SKILLED_TRADES -> 0xFFF59E0B.toInt() to 0xFFEF4444.toInt()   // Amber to Crimson
                EducationalCategory.MATHEMATICS -> 0xFF3B82F6.toInt() to 0xFF8B5CF6.toInt()      // Indigo to Purple
                EducationalCategory.NATURAL_SCIENCES -> 0xFF06B6D4.toInt() to 0xFF10B981.toInt() // Cyan to Mint
                EducationalCategory.HISTORY_CIVILIZATION -> 0xFFD97706.toInt() to 0xFFB45309.toInt() // Bronze to Ochre
                EducationalCategory.ARTS_HUMANITIES -> 0xFFEC4899.toInt() to 0xFF8B5CF6.toInt()  // Rose to Violet
                EducationalCategory.HEALTHCARE_SAFETY -> 0xFFEF4444.toInt() to 0xFF10B981.toInt() // Red to Green
                EducationalCategory.SCIENCE_ENGINEERING -> 0xFF0284C7.toInt() to 0xFF6366F1.toInt() // Blue to Cobalt
                EducationalCategory.BUSINESS_FINANCE -> 0xFF10B981.toInt() to 0xFFF59E0B.toInt()  // Green to Gold
                else -> 0xFF10B981.toInt() to 0xFF3B82F6.toInt()
            }

            // 2. Draw topic-specific procedural geometric motif
            val seed = (title.hashCode().toLong() and 0xFFFFFFFFL).toFloat()
            drawSubjectProceduralArt(canvas, width, height, category, accentColor, secondaryColor, seed)

            // 3. Draw subtle grid overlay
            val gridPaint = Paint().apply {
                isAntiAlias = true
                color = 0x14FFFFFF
                strokeWidth = 1f
                style = Paint.Style.STROKE
            }
            val step = 40f
            var x = 0f
            while (x <= width) {
                canvas.drawLine(x, 0f, x, height.toFloat(), gridPaint)
                x += step
            }
            var y = 0f
            while (y <= height) {
                canvas.drawLine(0f, y, width.toFloat(), y, gridPaint)
                y += step
            }

            // 4. Draw bottom dark vignette gradient for text contrast
            val vignettePaint = Paint().apply {
                isAntiAlias = true
                shader = AndroidLinearGradient(
                    0f, height * 0.4f, 0f, height.toFloat(),
                    intArrayOf(0x00000000, 0x99090B0E.toInt(), 0xFA090B0E.toInt()),
                    floatArrayOf(0f, 0.5f, 1f),
                    AndroidShader.TileMode.CLAMP
                )
            }
            canvas.drawRect(0f, height * 0.35f, width.toFloat(), height.toFloat(), vignettePaint)

            // 5. Draw Category Tag Badge at top-left
            val badgeBgPaint = Paint().apply {
                isAntiAlias = true
                color = 0xDD161D26.toInt()
                style = Paint.Style.FILL
            }
            val badgeBorderPaint = Paint().apply {
                isAntiAlias = true
                color = accentColor
                style = Paint.Style.STROKE
                strokeWidth = 2f
            }
            val badgeRect = RectF(36f, 32f, 36f + 260f, 74f)
            canvas.drawRoundRect(badgeRect, 12f, 12f, badgeBgPaint)
            canvas.drawRoundRect(badgeRect, 12f, 12f, badgeBorderPaint)

            val badgeTextPaint = Paint().apply {
                isAntiAlias = true
                color = accentColor
                textSize = 20f
                isFakeBoldText = true
            }
            val categoryLabel = "${category.emoji}  ${category.title.uppercase()}"
            canvas.drawText(categoryLabel, 54f, 62f, badgeTextPaint)

            // 6. Draw Course Title typography
            val titlePaint = Paint().apply {
                isAntiAlias = true
                color = 0xFFF1F5F9.toInt()
                textSize = if (title.length > 35) 30f else 36f
                isFakeBoldText = true
                setShadowLayer(8f, 0f, 4f, 0xFF000000.toInt())
            }

            // Word wrap title if needed
            val maxTextWidth = width - 80f
            val words = title.split(" ")
            val lines = mutableListOf<String>()
            var currentLine = StringBuilder()

            for (word in words) {
                val candidate = if (currentLine.isEmpty()) word else "$currentLine $word"
                if (titlePaint.measureText(candidate) <= maxTextWidth) {
                    currentLine = StringBuilder(candidate)
                } else {
                    if (currentLine.isNotEmpty()) lines.add(currentLine.toString())
                    currentLine = StringBuilder(word)
                }
            }
            if (currentLine.isNotEmpty()) lines.add(currentLine.toString())

            val startY = height - 44f - (lines.size - 1) * 42f
            lines.forEachIndexed { i, line ->
                canvas.drawText(line, 40f, startY + (i * 42f), titlePaint)
            }

            // Save to file
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 95, out)
            }
            bitmap.recycle()

            "file://${file.absolutePath}"
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private fun drawSubjectProceduralArt(
        canvas: android.graphics.Canvas,
        w: Int,
        h: Int,
        category: EducationalCategory,
        accent: Int,
        secondary: Int,
        seed: Float
    ) {
        val strokePaint = Paint().apply {
            isAntiAlias = true
            color = accent
            strokeWidth = 3f
            style = Paint.Style.STROKE
        }
        val fillPaint = Paint().apply {
            isAntiAlias = true
            color = secondary
            style = Paint.Style.FILL
            alpha = 40
        }

        when (category) {
            EducationalCategory.COMPUTER_SCIENCE -> {
                // Circuit traces & node network
                val centerX = w * 0.72f
                val centerY = h * 0.42f
                val path = AndroidPath()
                for (i in 0..6) {
                    val radius = 50f + i * 28f
                    canvas.drawCircle(centerX, centerY, radius, Paint().apply {
                        isAntiAlias = true
                        color = if (i % 2 == 0) accent else secondary
                        alpha = 50 + i * 20
                        style = Paint.Style.STROKE
                        strokeWidth = 2.5f
                    })
                }
                // Circuit traces branching out
                for (angle in listOf(0.0, 45.0, 90.0, 135.0, 180.0, 225.0, 270.0, 315.0)) {
                    val rad = Math.toRadians(angle)
                    val x1 = (centerX + cos(rad) * 60f).toFloat()
                    val y1 = (centerY + sin(rad) * 60f).toFloat()
                    val x2 = (centerX + cos(rad) * 220f).toFloat()
                    val y2 = (centerY + sin(rad) * 220f).toFloat()
                    canvas.drawLine(x1, y1, x2, y2, strokePaint)
                    canvas.drawCircle(x2, y2, 6f, Paint().apply {
                        isAntiAlias = true
                        color = accent
                        style = Paint.Style.FILL
                    })
                }
            }

            EducationalCategory.SKILLED_TRADES -> {
                // Mechanical gear silhouette & blueprint isometric angles
                val gearX = w * 0.75f
                val gearY = h * 0.42f
                val gearRadius = 110f
                val teeth = 12
                val gearPath = AndroidPath()

                for (i in 0 until teeth * 2) {
                    val angle = (i * PI / teeth).toDouble()
                    val r = if (i % 2 == 0) gearRadius + 22f else gearRadius - 10f
                    val px = (gearX + cos(angle) * r).toFloat()
                    val py = (gearY + sin(angle) * r).toFloat()
                    if (i == 0) gearPath.moveTo(px, py) else gearPath.lineTo(px, py)
                }
                gearPath.close()
                canvas.drawPath(gearPath, fillPaint)
                canvas.drawPath(gearPath, strokePaint)
                canvas.drawCircle(gearX, gearY, 40f, strokePaint)
                canvas.drawCircle(gearX, gearY, 18f, Paint().apply {
                    isAntiAlias = true
                    color = accent
                    style = Paint.Style.FILL
                })
            }

            EducationalCategory.MATHEMATICS -> {
                // Cartesian coordinate plane with sinusoidal and polynomial curves
                val startX = w * 0.45f
                val centerY = h * 0.45f
                val curvePath = AndroidPath()
                curvePath.moveTo(startX, centerY)
                for (i in 0..120) {
                    val t = i / 120f
                    val px = startX + t * (w * 0.5f)
                    val py = centerY + (sin(t * 4 * PI) * 80f).toFloat()
                    curvePath.lineTo(px, py)
                }
                canvas.drawPath(curvePath, strokePaint)

                // Secondary harmonic curve
                val curvePath2 = AndroidPath()
                curvePath2.moveTo(startX, centerY)
                for (i in 0..120) {
                    val t = i / 120f
                    val px = startX + t * (w * 0.5f)
                    val py = centerY + (cos(t * 3 * PI) * 55f).toFloat()
                    curvePath2.lineTo(px, py)
                }
                canvas.drawPath(curvePath2, Paint().apply {
                    isAntiAlias = true
                    color = secondary
                    strokeWidth = 2f
                    style = Paint.Style.STROKE
                })
            }

            EducationalCategory.NATURAL_SCIENCES -> {
                // DNA double helix spiral
                val startX = w * 0.55f
                val centerY = h * 0.45f
                for (i in 0..25) {
                    val t = i / 25f
                    val px = startX + t * (w * 0.4f)
                    val yTop = centerY + (sin(t * 5 * PI) * 65f).toFloat()
                    val yBottom = centerY - (sin(t * 5 * PI) * 65f).toFloat()
                    // Helix rungs
                    canvas.drawLine(px, yTop, px, yBottom, Paint().apply {
                        isAntiAlias = true
                        color = 0x55FFFFFF
                        strokeWidth = 2f
                    })
                    // Helix nodes
                    canvas.drawCircle(px, yTop, 5f, Paint().apply {
                        isAntiAlias = true
                        color = accent
                        style = Paint.Style.FILL
                    })
                    canvas.drawCircle(px, yBottom, 5f, Paint().apply {
                        isAntiAlias = true
                        color = secondary
                        style = Paint.Style.FILL
                    })
                }
            }

            EducationalCategory.HISTORY_CIVILIZATION -> {
                // Classical Greco-Roman temple columns
                val colStartX = w * 0.58f
                val colWidth = 36f
                val colHeight = 180f
                val colY = h * 0.18f

                for (i in 0..4) {
                    val cx = colStartX + i * 54f
                    val rect = RectF(cx, colY, cx + colWidth, colY + colHeight)
                    canvas.drawRect(rect, fillPaint)
                    canvas.drawRect(rect, strokePaint)
                    // Flutes
                    canvas.drawLine(cx + 12f, colY + 10f, cx + 12f, colY + colHeight - 10f, Paint().apply {
                        isAntiAlias = true
                        color = secondary
                        strokeWidth = 1.5f
                    })
                    canvas.drawLine(cx + 24f, colY + 10f, cx + 24f, colY + colHeight - 10f, Paint().apply {
                        isAntiAlias = true
                        color = secondary
                        strokeWidth = 1.5f
                    })
                }
                // Pediment roof arch
                val roof = AndroidPath()
                roof.moveTo(colStartX - 15f, colY)
                roof.lineTo(colStartX + 2 * 54f + colWidth / 2f, colY - 50f)
                roof.lineTo(colStartX + 4 * 54f + colWidth + 15f, colY)
                roof.close()
                canvas.drawPath(roof, fillPaint)
                canvas.drawPath(roof, strokePaint)
            }

            EducationalCategory.ARTS_HUMANITIES -> {
                // Golden ratio logarithmic spiral and geometric arcs
                val spiralX = w * 0.72f
                val spiralY = h * 0.45f
                val spiralPath = AndroidPath()
                for (deg in 0..720 step 5) {
                    val rad = Math.toRadians(deg.toDouble())
                    val r = 8f * Math.exp(0.0035 * deg)
                    val px = (spiralX + cos(rad) * r).toFloat()
                    val py = (spiralY + sin(rad) * r).toFloat()
                    if (deg == 0) spiralPath.moveTo(px, py) else spiralPath.lineTo(px, py)
                }
                canvas.drawPath(spiralPath, strokePaint)

                for (r in listOf(60f, 110f, 160f)) {
                    canvas.drawCircle(spiralX, spiralY, r, Paint().apply {
                        isAntiAlias = true
                        color = secondary
                        alpha = 45
                        style = Paint.Style.STROKE
                        strokeWidth = 2f
                    })
                }
            }

            else -> {
                // Dynamic geometric constellation
                val cx = w * 0.72f
                val cy = h * 0.42f
                for (i in 0..7) {
                    val angle = (i * 45 * PI / 180.0)
                    val px = (cx + cos(angle) * 120f).toFloat()
                    val py = (cy + sin(angle) * 100f).toFloat()
                    canvas.drawLine(cx, cy, px, py, strokePaint)
                    canvas.drawCircle(px, py, 8f, Paint().apply {
                        isAntiAlias = true
                        color = secondary
                        style = Paint.Style.FILL
                    })
                }
                canvas.drawCircle(cx, cy, 18f, Paint().apply {
                    isAntiAlias = true
                    color = accent
                    style = Paint.Style.FILL
                })
            }
        }
    }
}

/**
 * Robust Composable Thumbnail viewer:
 * Loads the saved generated bitmap via Coil AsyncImage if available.
 * If not yet generated, dynamically renders a live procedural Canvas thumbnail with
 * identical thematic beauty matching the course title and category!
 */
@Composable
fun CourseThumbnail(
    course: Course,
    modifier: Modifier = Modifier,
    aspectRatio: Float = 16f / 9f
) {
    val context = LocalContext.current

    // Ensure thumbnail file exists or generate on-demand if null/missing
    val thumbnailUri = remember(course.id, course.title, course.thumbnailUri) {
        if (!course.thumbnailUri.isNullOrBlank()) {
            val path = course.thumbnailUri.removePrefix("file://")
            if (File(path).exists()) {
                course.thumbnailUri
            } else {
                CourseThumbnailGenerator.generateAndSaveThumbnail(
                    context,
                    course.id,
                    course.title,
                    course.topic,
                    course.category
                )
            }
        } else {
            CourseThumbnailGenerator.generateAndSaveThumbnail(
                context,
                course.id,
                course.title,
                course.topic,
                course.category
            )
        }
    }

    Box(
        modifier = modifier
            .aspectRatio(aspectRatio)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0F141C))
    ) {
        if (!thumbnailUri.isNullOrBlank()) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(thumbnailUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "${course.title} thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Live procedural fallback canvas
            ProceduralCourseCanvas(course = course)
        }
    }
}

@Composable
fun ProceduralCourseCanvas(
    course: Course,
    modifier: Modifier = Modifier
) {
    val category = course.category
    val (accentColor, secondaryColor) = remember(category) {
        when (category) {
            EducationalCategory.COMPUTER_SCIENCE -> Color(0xFF10B981) to Color(0xFF06B6D4)
            EducationalCategory.SKILLED_TRADES -> Color(0xFFF59E0B) to Color(0xFFEF4444)
            EducationalCategory.MATHEMATICS -> Color(0xFF3B82F6) to Color(0xFF8B5CF6)
            EducationalCategory.NATURAL_SCIENCES -> Color(0xFF06B6D4) to Color(0xFF10B981)
            EducationalCategory.HISTORY_CIVILIZATION -> Color(0xFFD97706) to Color(0xFFB45309)
            EducationalCategory.ARTS_HUMANITIES -> Color(0xFFEC4899) to Color(0xFF8B5CF6)
            EducationalCategory.HEALTHCARE_SAFETY -> Color(0xFFEF4444) to Color(0xFF10B981)
            EducationalCategory.SCIENCE_ENGINEERING -> Color(0xFF0284C7) to Color(0xFF6366F1)
            EducationalCategory.BUSINESS_FINANCE -> Color(0xFF10B981) to Color(0xFFF59E0B)
            else -> Color(0xFF10B981) to Color(0xFF3B82F6)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF090B0E),
                        Color(0xFF13171E),
                        Color(0xFF1A222B)
                    )
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Subtle grid
            val step = 30f
            var x = 0f
            while (x <= w) {
                drawLine(
                    color = Color.White.copy(alpha = 0.05f),
                    start = Offset(x, 0f),
                    end = Offset(x, h),
                    strokeWidth = 1f
                )
                x += step
            }
            var y = 0f
            while (y <= h) {
                drawLine(
                    color = Color.White.copy(alpha = 0.05f),
                    start = Offset(0f, y),
                    end = Offset(w, y),
                    strokeWidth = 1f
                )
                y += step
            }

            // Central motif
            val cx = w * 0.7f
            val cy = h * 0.45f
            drawCircle(
                color = accentColor.copy(alpha = 0.25f),
                radius = 70f,
                center = Offset(cx, cy),
                style = Stroke(width = 3f)
            )
            drawCircle(
                color = secondaryColor.copy(alpha = 0.2f),
                radius = 110f,
                center = Offset(cx, cy),
                style = Stroke(width = 2f)
            )
            drawCircle(
                color = accentColor,
                radius = 12f,
                center = Offset(cx, cy)
            )
        }

        // Bottom vignette
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x99090B0E),
                            Color(0xFA090B0E)
                        ),
                        startY = 100f
                    )
                )
        )

        // Title and Category overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xDD161D26),
                border = androidx.compose.foundation.BorderStroke(1.dp, accentColor)
            ) {
                Text(
                    text = "${category.emoji} ${category.title.uppercase()}",
                    color = accentColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = course.title,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
