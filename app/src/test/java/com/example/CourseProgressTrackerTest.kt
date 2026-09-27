package com.example

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.data.remote.OfflineCourseTemplates
import com.example.ui.components.CourseProgressTracker
import com.example.ui.theme.MyApplicationTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class CourseProgressTrackerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCourseProgressTrackerRendersCorrectly() {
        val course = OfflineCourseTemplates.getCuratedStarterCourses().first()
        val sections = course.sections
        val flashcards = course.flashcards
        var clickedNext = false

        composeTestRule.setContent {
            MyApplicationTheme {
                CourseProgressTracker(
                    course = course,
                    sections = sections,
                    flashcards = flashcards,
                    attempts = emptyList(),
                    onResumeNextAction = { clickedNext = true }
                )
            }
        }

        // Verify the progress tracker card and elements are displayed
        composeTestRule.onNodeWithTag("course_progress_tracker").assertIsDisplayed()
        composeTestRule.onNodeWithTag("progress_status_badge").assertIsDisplayed()
        composeTestRule.onNodeWithTag("progress_bar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("next_action_button").assertIsDisplayed()

        // Perform click on Continue button
        composeTestRule.onNodeWithTag("next_action_button").performClick()
        assertTrue(clickedNext)
    }
}
