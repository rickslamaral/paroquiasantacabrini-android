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

package br.com.paroquiasantacabrini.app.core.designsystem.component

import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35], qualifiers = "w411dp-h900dp")
public class ParoquiaPageTest {
    @get:Rule
    public val composeTestRule = createComposeRule()

    @Test
    public fun pageShowsEyebrowTitleAndContent() {
        composeTestRule.setContent {
            ParoquiaPage(title = "Rosary", eyebrow = "Prayer") {
                Text("Body")
            }
        }

        composeTestRule.onNodeWithText("Prayer").assertIsDisplayed()
        composeTestRule.onNodeWithText("Rosary").assertIsDisplayed()
        composeTestRule.onNodeWithText("Body").assertIsDisplayed()
    }

    @Test
    public fun backButtonIsAnnouncedAndInvokesCallback() {
        var backClicks = 0
        composeTestRule.setContent {
            ParoquiaPage(title = "Rosary", onBackClick = { backClicks += 1 }) {}
        }

        composeTestRule.onNodeWithContentDescription("Back").performClick()

        composeTestRule.runOnIdle { assertEquals(1, backClicks) }
    }

    @Test
    public fun navCardShowsTextAndInvokesCallback() {
        var clicks = 0
        composeTestRule.setContent {
            NavCard(
                title = "Novenas",
                description = "Keep praying",
                emoji = "✧",
                onClick = { clicks += 1 },
            )
        }

        composeTestRule.onNodeWithText("Keep praying").assertIsDisplayed()
        composeTestRule.onNodeWithText("Novenas").performClick()

        composeTestRule.runOnIdle { assertEquals(1, clicks) }
    }

    @Test
    public fun calloutShowsItsText() {
        composeTestRule.setContent { Callout(text = "Demo content") }

        composeTestRule.onNodeWithText("Demo content").assertIsDisplayed()
    }
}
