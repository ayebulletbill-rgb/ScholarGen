package com.example.ui.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Supported display modes for the educational study environment.
 */
enum class AppThemeMode(
    val title: String,
    val subtitle: String,
    val description: String
) {
    SYSTEM(
        title = "System Default",
        subtitle = "Automatic",
        description = "Aligns with your Android device appearance settings."
    ),
    LIGHT(
        title = "Daylight Focus",
        subtitle = "High Clarity",
        description = "Crisp, anti-glare high contrast optimized for bright daytime study."
    ),
    DARK(
        title = "Nocturnal Study",
        subtitle = "Eye Comfort",
        description = "Deep slate canvas with reduced blue luminescence for extended study sessions without eye fatigue."
    )
}

/**
 * Manages theme state and local persistence using SharedPreferences.
 */
class ThemeManager private constructor(context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _themeMode = MutableStateFlow(loadSavedThemeMode())
    val themeMode: StateFlow<AppThemeMode> = _themeMode.asStateFlow()

    fun setThemeMode(mode: AppThemeMode) {
        _themeMode.value = mode
        prefs.edit().putString(KEY_THEME_MODE, mode.name).apply()
    }

    /**
     * Toggles between Light and Dark mode.
     * If currently in System, switches to Dark for instant eye relief.
     */
    fun toggleTheme() {
        val nextMode = when (_themeMode.value) {
            AppThemeMode.LIGHT -> AppThemeMode.DARK
            AppThemeMode.DARK -> AppThemeMode.LIGHT
            AppThemeMode.SYSTEM -> AppThemeMode.DARK
        }
        setThemeMode(nextMode)
    }

    private fun loadSavedThemeMode(): AppThemeMode {
        val saved = prefs.getString(KEY_THEME_MODE, AppThemeMode.DARK.name)
        return try {
            AppThemeMode.valueOf(saved ?: AppThemeMode.DARK.name)
        } catch (_: Exception) {
            AppThemeMode.DARK
        }
    }

    companion object {
        private const val PREFS_NAME = "study_theme_preferences"
        private const val KEY_THEME_MODE = "active_study_theme_mode"

        @Volatile
        private var instance: ThemeManager? = null

        fun getInstance(context: Context): ThemeManager {
            return instance ?: synchronized(this) {
                instance ?: ThemeManager(context).also { instance = it }
            }
        }
    }
}

val LocalThemeManager = staticCompositionLocalOf<ThemeManager?> {
    null
}

val LocalStudyColors = staticCompositionLocalOf<StudyFeedbackColors> {
    LightFeedbackColors
}

/**
 * Helper to query active study feedback colors directly.
 */
object StudyTheme {
    val feedbackColors: StudyFeedbackColors
        @Composable
        @ReadOnlyComposable
        get() = LocalStudyColors.current
}

/**
 * Primary theme provider composable.
 * Wraps the application in MaterialTheme, calculates light vs dark mode,
 * and distributes the ThemeManager and StudyFeedbackColors through CompositionLocals.
 */
@Composable
fun AppThemeProvider(
    themeManager: ThemeManager = ThemeManager.getInstance(LocalContext.current),
    content: @Composable () -> Unit
) {
    val themeMode by themeManager.themeMode.collectAsState()
    val systemDark = isSystemInDarkTheme()

    val isDark = when (themeMode) {
        AppThemeMode.LIGHT -> false
        AppThemeMode.DARK -> true
        AppThemeMode.SYSTEM -> systemDark
    }

    val feedbackColors = if (isDark) DarkFeedbackColors else LightFeedbackColors

    CompositionLocalProvider(
        LocalThemeManager provides themeManager,
        LocalStudyColors provides feedbackColors
    ) {
        MyApplicationTheme(
            darkTheme = isDark,
            dynamicColor = false // Consistent study color calibration
        ) {
            content()
        }
    }
}
