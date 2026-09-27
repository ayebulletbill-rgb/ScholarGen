package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Legacy default tokens (preserved for backward compatibility)
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// -------------------------------------------------------------
// Study Clarity Theme Colors (Light Mode - Daytime Focus)
// -------------------------------------------------------------
val LightStudyPrimary = Color(0xFF1D4ED8)           // Rich Indigo-Blue (high WCAG AAA contrast)
val LightStudyOnPrimary = Color(0xFFFFFFFF)
val LightStudyPrimaryContainer = Color(0xFFDBEAFE)  // Soft sky tint
val LightStudyOnPrimaryContainer = Color(0xFF1E3A8A)

val LightStudySecondary = Color(0xFF0284C7)         // Deep Cyan
val LightStudyOnSecondary = Color(0xFFFFFFFF)
val LightStudySecondaryContainer = Color(0xFFE0F2FE)
val LightStudyOnSecondaryContainer = Color(0xFF075985)

val LightStudyTertiary = Color(0xFFB45309)          // Warm Amber
val LightStudyOnTertiary = Color(0xFFFFFFFF)
val LightStudyTertiaryContainer = Color(0xFFFEF3C7)
val LightStudyOnTertiaryContainer = Color(0xFF78350F)

val LightStudyBackground = Color(0xFFF8FAFC)        // Anti-glare soft slate canvas
val LightStudyOnBackground = Color(0xFF0F172A)      // Deep crisp slate
val LightStudySurface = Color(0xFFFFFFFF)
val LightStudyOnSurface = Color(0xFF0F172A)
val LightStudySurfaceVariant = Color(0xFFF1F5F9)
val LightStudyOnSurfaceVariant = Color(0xFF475569)  // Legible secondary text

val LightStudyOutline = Color(0xFF94A3B8)
val LightStudyOutlineVariant = Color(0xFFE2E8F0)

// -------------------------------------------------------------
// Nocturnal Eye-Comfort Colors (Black/Grey Base, Dark Green Secondary, Red Third)
// -------------------------------------------------------------
val DarkStudyPrimary = Color(0xFF10B981)            // Vibrant Emerald / Mint accent
val DarkStudyOnPrimary = Color(0xFFFFFFFF)
val DarkStudyPrimaryContainer = Color(0xFF0A2B1D)   // Deep Forest Pine
val DarkStudyOnPrimaryContainer = Color(0xFFA7F3D0)

val DarkStudySecondary = Color(0xFF059669)          // Dark Green secondary
val DarkStudyOnSecondary = Color(0xFFFFFFFF)
val DarkStudySecondaryContainer = Color(0xFF0F3822) // Dark Green Container
val DarkStudyOnSecondaryContainer = Color(0xFFD1FAE5)

val DarkStudyTertiary = Color(0xFFEF4444)           // Crimson Red third accent
val DarkStudyOnTertiary = Color(0xFFFFFFFF)
val DarkStudyTertiaryContainer = Color(0xFF7F1D1D)  // Deep Red Container
val DarkStudyOnTertiaryContainer = Color(0xFFFEE2E2)

val DarkStudyBackground = Color(0xFF090B0E)         // Deep Obsidian Black base
val DarkStudyOnBackground = Color(0xFFF3F4F6)       // Crisp white text
val DarkStudySurface = Color(0xFF13171D)            // Dark Charcoal / Slate card surface
val DarkStudyOnSurface = Color(0xFFF3F4F6)
val DarkStudySurfaceVariant = Color(0xFF1B2129)     // Elevated Dark Grey container
val DarkStudyOnSurfaceVariant = Color(0xFF9CA3AF)   // Legible silver/grey secondary text

val DarkStudyOutline = Color(0xFF2E3844)
val DarkStudyOutlineVariant = Color(0xFF1F252E)

// -------------------------------------------------------------
// Modular Dashboard Dedicated Tokens
// -------------------------------------------------------------
object DashboardTokens {
    val CanvasBase = Color(0xFF090B0E)
    val CardBackground = Color(0xFF13171D)
    val CardBackgroundElevated = Color(0xFF1B2129)
    val CardBorder = Color(0xFF262E38)
    val CardBorderLight = Color(0xFF333E4C)

    // Green (Secondary)
    val DarkGreenHeader = Color(0xFF0D2B1D)
    val ForestGreen = Color(0xFF134E31)
    val EmeraldGreen = Color(0xFF10B981)
    val MintGreen = Color(0xFF22C55E)
    val LightMint = Color(0xFF86EFAC)

    // Red (Third)
    val CrimsonRed = Color(0xFFDC2626)
    val DarkRedHeader = Color(0xFF7F1D1D)
    val BrightRed = Color(0xFFEF4444)
    val CoralRed = Color(0xFFF87171)
    val LightRed = Color(0xFFFEE2E2)

    // Slate & Neutrals
    val SlateBlueDark = Color(0xFF1E2530)
    val TextWhite = Color(0xFFF9FAFB)
    val TextGrey = Color(0xFF9CA3AF)
    val TextMuted = Color(0xFF6B7280)

    // Chart & Donut Segments
    val SegmentGreen = Color(0xFF10B981)
    val SegmentTeal = Color(0xFF14B8A6)
    val SegmentRed = Color(0xFFEF4444)
    val SegmentDarkGreen = Color(0xFF059669)
}

// -------------------------------------------------------------
// Adaptive Study Semantic Tokens (Status / Feedback / Badges)
// -------------------------------------------------------------
data class StudyFeedbackColors(
    val success: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,
    val error: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val warning: Color,
    val warningContainer: Color,
    val onWarningContainer: Color
)

val LightFeedbackColors = StudyFeedbackColors(
    success = Color(0xFF16A34A),
    successContainer = Color(0xFFDCFCE7),
    onSuccessContainer = Color(0xFF14532D),
    error = Color(0xFFDC2626),
    errorContainer = Color(0xFFFEE2E2),
    onErrorContainer = Color(0xFF7F1D1D),
    warning = Color(0xFFD97706),
    warningContainer = Color(0xFFFEF3C7),
    onWarningContainer = Color(0xFF78350F)
)

val DarkFeedbackColors = StudyFeedbackColors(
    success = Color(0xFF4ADE80),
    successContainer = Color(0xFF14532D).copy(alpha = 0.6f),
    onSuccessContainer = Color(0xFF86EFAC),
    error = Color(0xFFF87171),
    errorContainer = Color(0xFF7F1D1D).copy(alpha = 0.6f),
    onErrorContainer = Color(0xFFFECACA),
    warning = Color(0xFFFBBF24),
    warningContainer = Color(0xFF78350F).copy(alpha = 0.6f),
    onWarningContainer = Color(0xFFFDE68A)
)

