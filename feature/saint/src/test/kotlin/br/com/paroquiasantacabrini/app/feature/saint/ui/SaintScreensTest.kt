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

package br.com.paroquiasantacabrini.app.feature.saint.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35], qualifiers = "w411dp-h1600dp")
public class SaintScreensTest {

    @get:Rule
    public val composeTestRule = createComposeRule()

    @Test
    public fun cabriniPageShowsMemorialFactsSourcesAndDemoNote() {
        composeTestRule.setContent {
            MaterialTheme {
                SaintCabriniRoute(onVisitCommunityClick = {}, onBackClick = {})
            }
        }

        composeTestRule.onNodeWithText("Liturgical memorial · December 22").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Dec 22, 1917").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Vatican").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Illustrative images, not historical photographs.")
            .performScrollTo()
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(
            "Summary for demonstration. The text must be reviewed by the parish before official publication.",
        ).performScrollTo().assertIsDisplayed()
    }

    @Test
    public fun visitCommunityForwardsIntent() {
        var clicks = 0
        composeTestRule.setContent {
            MaterialTheme {
                SaintCabriniRoute(onVisitCommunityClick = { clicks += 1 }, onBackClick = {})
            }
        }

        composeTestRule.onNodeWithText("Visit the community").performScrollTo().performClick()

        assertThat(clicks).isEqualTo(1)
    }

    @Test
    public fun searchFiltersTheDirectoryAndShowsEmptyState() {
        composeTestRule.setContent {
            MaterialTheme { SaintsRoute(onSeeCabriniClick = {}, onBackClick = {}) }
        }

        composeTestRule.onNodeWithText("Search by name").performTextInput("antonio")
        composeTestRule.onNodeWithText("Santo Antônio de Pádua").assertIsDisplayed()

        composeTestRule.onNodeWithText("Search by name").performTextInput("zzz")
        composeTestRule.onNodeWithText("No saint found in this sample.").assertIsDisplayed()
    }

    @Test
    public fun demoProfileOpensInlineAndPatroessOpensItsPage() {
        var cabriniClicks = 0
        composeTestRule.setContent {
            MaterialTheme {
                SaintsRoute(onSeeCabriniClick = { cabriniClicks += 1 }, onBackClick = {})
            }
        }

        composeTestRule.onNodeWithText("São José").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Demonstration profile").performScrollTo().assertIsDisplayed()

        composeTestRule.onNodeWithText("Santa Francisca Xavier Cabrini").performScrollTo().performClick()
        assertThat(cabriniClicks).isEqualTo(1)
    }
}
