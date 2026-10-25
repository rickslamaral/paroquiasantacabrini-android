/*
 * Designed and developed by 2026 ashtanko (Oleksii Shtanko)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.paroquiasantacabrini.app

import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.test.espresso.Espresso
import br.com.paroquiasantacabrini.app.home.navigation.MainNavigation
import br.com.paroquiasantacabrini.app.home.navigation.Screen
import org.junit.Rule
import org.junit.Test

class MainNavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigationFlow_FromHomeToDailyLiturgy() {
        composeTestRule.setContent {
            MainNavigation(postsContent = { Text("Posts") })
        }

        composeTestRule.onNodeWithText("Liturgy").performClick()

        composeTestRule.onNodeWithText("Prayer guide").assertIsDisplayed()
    }

    @Test
    fun testNavigationFlow_FromHomeToPrayerRequest() {
        composeTestRule.setContent {
            MainNavigation(postsContent = { Text("Posts") })
        }

        composeTestRule.onNodeWithText("Prayer").performClick()

        composeTestRule.onNodeWithText("Your intention").assertIsDisplayed()
    }

    @Test
    fun testNavigationFlow_FromHomeToPix() {
        composeTestRule.setContent {
            MainNavigation(postsContent = { Text("Posts") })
        }

        composeTestRule.onNodeWithText("Tithes").performClick()

        composeTestRule.onNodeWithText("Copy Pix key").assertIsDisplayed()
    }

    @Test
    fun testNavigationFlow_FromHomeToMassSchedule() {
        composeTestRule.setContent {
            MainNavigation(postsContent = { Text("Posts") })
        }

        composeTestRule.onNodeWithText("Times").performClick()

        composeTestRule.onNodeWithText("Masses and celebrations").assertIsDisplayed()
    }

    @Test
    fun testBottomBar_SwitchesBetweenSections() {
        composeTestRule.setContent {
            MainNavigation(postsContent = { Text("Posts") })
        }

        composeTestRule.onNodeWithText("Pray").performClick()
        composeTestRule.onNodeWithText("A moment of prayer").assertIsDisplayed()

        composeTestRule.onNodeWithText("More").performClick()
        composeTestRule.onNodeWithText("Explore").assertIsDisplayed()

        composeTestRule.onNodeWithText("Home").performClick()
        composeTestRule.onNodeWithText("So good to have you here.").assertIsDisplayed()
    }

    @Test
    fun testBackNavigation_RemovesEntries() {
        composeTestRule.setContent {
            MainNavigation(postsContent = { Text("Posts") })
        }

        // Navigate to a destination
        composeTestRule.onNodeWithText("Times").performClick()
        composeTestRule.onNodeWithText("Masses and celebrations").assertIsDisplayed()

        // Trigger system back (Navigation3 handles this via the onBack lambda)
        Espresso.pressBack()

        // Verify we are back on the home dashboard
        composeTestRule.onNodeWithText("So good to have you here.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Masses and celebrations").assertDoesNotExist()
    }

    @Test
    fun testStartAtScreenB() {
        composeTestRule.setContent {
            // Testability: we can start at any screen by providing a custom backstack
            val backStack = rememberNavBackStack(Screen.ScreenB)
            MainNavigation(
                backStack = backStack,
                postsContent = { Text("Posts") },
            )
        }

        // Verify we start on posts
        composeTestRule.onNodeWithText("Posts").assertIsDisplayed()
    }
}
