package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.ui.theme.AppThemeMode
import com.example.ui.theme.DarkFeedbackColors
import com.example.ui.theme.DarkStudyBackground
import com.example.ui.theme.DarkStudyPrimary
import com.example.ui.theme.LightFeedbackColors
import com.example.ui.theme.LightStudyBackground
import com.example.ui.theme.LightStudyPrimary
import com.example.ui.theme.ThemeManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ThemeProviderUnitTest {

    @Test
    fun testThemeManagerDefaultAndToggle() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val themeManager = ThemeManager.getInstance(context)

        // Reset to initial mode
        themeManager.setThemeMode(AppThemeMode.SYSTEM)
        assertEquals(AppThemeMode.SYSTEM, themeManager.themeMode.value)

        // Toggle from SYSTEM should go to DARK for nighttime study comfort
        themeManager.toggleTheme()
        assertEquals(AppThemeMode.DARK, themeManager.themeMode.value)

        // Toggle from DARK should go to LIGHT
        themeManager.toggleTheme()
        assertEquals(AppThemeMode.LIGHT, themeManager.themeMode.value)

        // Toggle from LIGHT should go to DARK
        themeManager.toggleTheme()
        assertEquals(AppThemeMode.DARK, themeManager.themeMode.value)
    }

    @Test
    fun testThemeExplicitModes() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val themeManager = ThemeManager.getInstance(context)

        themeManager.setThemeMode(AppThemeMode.LIGHT)
        assertEquals(AppThemeMode.LIGHT, themeManager.themeMode.value)

        themeManager.setThemeMode(AppThemeMode.DARK)
        assertEquals(AppThemeMode.DARK, themeManager.themeMode.value)

        themeManager.setThemeMode(AppThemeMode.SYSTEM)
        assertEquals(AppThemeMode.SYSTEM, themeManager.themeMode.value)
    }

    @Test
    fun testThemeColorTokensIntegrity() {
        // Light and Dark palettes must have distinct backgrounds and primary accents
        assertNotEquals(LightStudyBackground, DarkStudyBackground)
        assertNotEquals(LightStudyPrimary, DarkStudyPrimary)

        // Feedback colors must be non-null and distinct
        assertNotNull(LightFeedbackColors.success)
        assertNotNull(DarkFeedbackColors.success)
        assertNotNull(LightFeedbackColors.error)
        assertNotNull(DarkFeedbackColors.error)
    }
}
