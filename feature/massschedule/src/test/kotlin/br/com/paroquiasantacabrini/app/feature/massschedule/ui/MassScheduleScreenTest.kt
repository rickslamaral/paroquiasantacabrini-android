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

package br.com.paroquiasantacabrini.app.feature.massschedule.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import br.com.paroquiasantacabrini.app.feature.massschedule.ui.model.MassScheduleUiState
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35], qualifiers = "w411dp-h1600dp")
public class MassScheduleScreenTest {

    @get:Rule
    public val composeTestRule = createComposeRule()

    @Test
    public fun opensOnTheRequestedDayAndListsItsMasses() {
        setScheduleContent(initialDayIndex = 2)

        dayTab("Sunday").assertIsSelected()
        composeTestRule.onNodeWithText("08h").assertIsDisplayed()
        composeTestRule.onNodeWithText("10h30").assertIsDisplayed()
        composeTestRule.onNodeWithText("18h").assertIsDisplayed()
    }

    @Test
    public fun choosingADayTabShowsItsMasses() {
        setScheduleContent(initialDayIndex = 2)

        dayTab("Tuesday to Friday").performClick()

        dayTab("Tuesday to Friday").assertIsSelected()
        composeTestRule.onNodeWithText("Wednesday · Parish church").assertIsDisplayed()
        composeTestRule.onNodeWithText("10h30").assertDoesNotExist()
    }

    @Test
    public fun showsAdorationConfessionsAndTheParishOffice() {
        setScheduleContent()

        composeTestRule.onNodeWithText("Adoration of the Blessed Sacrament")
            .performScrollTo()
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Thursday, at 18h").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Confessions").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Tuesday to Friday, 14h–18h · Saturday, 08h–12h")
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    public fun directionsButtonAsksToOpenTheContactScreen() {
        var clicks = 0
        setScheduleContent(onDirectionsClick = { clicks += 1 })

        composeTestRule.onNodeWithText("How to get to the parish").performScrollTo().performClick()

        assertThat(clicks).isEqualTo(1)
    }

    private fun dayTab(label: String) = composeTestRule.onNode(hasText(label) and isSelectable())

    private fun setScheduleContent(
        initialDayIndex: Int = 0,
        onDirectionsClick: () -> Unit = {},
    ) {
        composeTestRule.setContent {
            MaterialTheme {
                MassScheduleScreen(
                    state = MassScheduleUiState.forParish(),
                    initialDayIndex = initialDayIndex,
                    onDirectionsClick = onDirectionsClick,
                )
            }
        }
    }
}
