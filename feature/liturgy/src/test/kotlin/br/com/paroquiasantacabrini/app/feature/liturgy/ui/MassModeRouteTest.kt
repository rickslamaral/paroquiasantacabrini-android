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

package br.com.paroquiasantacabrini.app.feature.liturgy.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35], qualifiers = "w411dp-h1600dp")
public class MassModeRouteTest {

    @get:Rule
    public val composeTestRule = createComposeRule()

    @Test
    public fun startsAtTheIntroductoryRitesAndCannotGoBack() {
        setContent()

        composeTestRule.onNodeWithText("Mass Mode").assertIsDisplayed()
        composeTestRule.onNodeWithText("Part 1 of 4").assertIsDisplayed()
        composeTestRule.onNodeWithText("‹ Previous").performScrollTo().assertIsNotEnabled()
    }

    @Test
    public fun nextAdvancesThroughTheWholeCelebration() {
        setContent()

        repeat(3) { composeTestRule.onNodeWithText("Next part ›").performScrollTo().performClick() }

        composeTestRule.onNodeWithText("Part 4 of 4").assertIsDisplayed()
        composeTestRule.onNodeWithText("Next part ›").performScrollTo().assertIsNotEnabled()
        composeTestRule.onNodeWithText("‹ Previous").performScrollTo().assertIsEnabled()
    }

    @Test
    public fun tappingAPartJumpsToIt() {
        setContent()

        composeTestRule.onNodeWithText("Liturgy of the Eucharist").performScrollTo().performClick()

        composeTestRule.onNodeWithText("Part 3 of 4").assertIsDisplayed()
    }

    @Test
    public fun textSizeControlsAreAnnouncedAndBounded() {
        setContent()

        repeat(10) { composeTestRule.onNodeWithContentDescription("Increase text size").performClick() }

        composeTestRule.onNodeWithContentDescription("Increase text size").assertIsNotEnabled()
        composeTestRule.onNodeWithContentDescription("Decrease text size").assertIsEnabled()
    }

    @Test
    public fun backButtonInvokesCallback() {
        var backClicks = 0
        setContent(onBackClick = { backClicks += 1 })

        composeTestRule.onNodeWithContentDescription("Back").performClick()

        assertThat(backClicks).isEqualTo(1)
    }

    @Test
    public fun showsTheDemonstrationDisclaimer() {
        setContent()

        composeTestRule.onNodeWithText(
            "Demonstration outline to follow the celebration. The full liturgical texts must " +
                "follow the official edition approved by the Church.",
        ).performScrollTo().assertIsDisplayed()
    }

    private fun setContent(onBackClick: () -> Unit = {}) {
        composeTestRule.setContent {
            MaterialTheme { MassModeRoute(onBackClick = onBackClick) }
        }
    }
}
