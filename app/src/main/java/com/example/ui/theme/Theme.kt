package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkStudyPrimary,
    onPrimary = DarkStudyOnPrimary,
    primaryContainer = DarkStudyPrimaryContainer,
    onPrimaryContainer = DarkStudyOnPrimaryContainer,
    secondary = DarkStudySecondary,
    onSecondary = DarkStudyOnSecondary,
    secondaryContainer = DarkStudySecondaryContainer,
    onSecondaryContainer = DarkStudyOnSecondaryContainer,
    tertiary = DarkStudyTertiary,
    onTertiary = DarkStudyOnTertiary,
    tertiaryContainer = DarkStudyTertiaryContainer,
    onTertiaryContainer = DarkStudyOnTertiaryContainer,
    background = DarkStudyBackground,
    onBackground = DarkStudyOnBackground,
    surface = DarkStudySurface,
    onSurface = DarkStudyOnSurface,
    surfaceVariant = DarkStudySurfaceVariant,
    onSurfaceVariant = DarkStudyOnSurfaceVariant,
    outline = DarkStudyOutline,
    outlineVariant = DarkStudyOutlineVariant
)

private val LightColorScheme = lightColorScheme(
    primary = LightStudyPrimary,
    onPrimary = LightStudyOnPrimary,
    primaryContainer = LightStudyPrimaryContainer,
    onPrimaryContainer = LightStudyOnPrimaryContainer,
    secondary = LightStudySecondary,
    onSecondary = LightStudyOnSecondary,
    secondaryContainer = LightStudySecondaryContainer,
    onSecondaryContainer = LightStudyOnSecondaryContainer,
    tertiary = LightStudyTertiary,
    onTertiary = LightStudyOnTertiary,
    tertiaryContainer = LightStudyTertiaryContainer,
    onTertiaryContainer = LightStudyOnTertiaryContainer,
    background = LightStudyBackground,
    onBackground = LightStudyOnBackground,
    surface = LightStudySurface,
    onSurface = LightStudyOnSurface,
    surfaceVariant = LightStudySurfaceVariant,
    onSurfaceVariant = LightStudyOnSurfaceVariant,
    outline = LightStudyOutline,
    outlineVariant = LightStudyOutlineVariant
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
