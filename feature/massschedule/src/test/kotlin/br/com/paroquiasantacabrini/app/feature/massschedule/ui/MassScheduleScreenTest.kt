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
    public fun showsMassTimesAdorationAndOfficeHours() {
        setScheduleContent()

        composeTestRule.onNodeWithText("Mass Times").assertIsDisplayed()
        composeTestRule.onNodeWithText("08h, 10h30, 18h").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Adoration of the Blessed Sacrament (Thursday)")
            .performScrollTo()
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("14h to 18h").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("08h to 12h").performScrollTo().assertIsDisplayed()
    }

    @Test
    public fun showsContactDetails() {
        setScheduleContent()

        composeTestRule.onNodeWithText("(11) 5928-9576").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Pe. Vinícius Henrique Maciel")
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    public fun contactActionsForwardPhoneEmailAndMapUris() {
        val opened = mutableListOf<String>()
        setScheduleContent(onActionClick = { opened += it })

        composeTestRule.onNodeWithText("Call the parish").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Send an e-mail").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Open in maps").performScrollTo().performClick()

        assertThat(opened).hasSize(3)
        assertThat(opened[0]).isEqualTo("tel:+551159289576")
        assertThat(opened[1]).isEqualTo("mailto:santafranciscaxaviercabrini@santoamaro.org.br")
        assertThat(opened[2]).startsWith("https://www.google.com/maps/search/?api=1&query=")
    }

    private fun setScheduleContent(onActionClick: (String) -> Unit = {}) {
        composeTestRule.setContent {
            MaterialTheme {
                MassScheduleScreen(
                    state = MassScheduleUiState.forParish(),
                    onActionClick = onActionClick,
                )
            }
        }
    }
}
