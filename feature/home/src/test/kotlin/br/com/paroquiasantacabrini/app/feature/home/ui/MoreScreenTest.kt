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

package br.com.paroquiasantacabrini.app.feature.home.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import br.com.paroquiasantacabrini.app.feature.home.ui.model.MoreDestination
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35], qualifiers = "w411dp-h2000dp")
public class MoreScreenTest {

    @get:Rule
    public val composeTestRule = createComposeRule()

    @Test
    public fun everyDestinationTileForwardsItsDestination() {
        val clicked = mutableListOf<MoreDestination>()
        composeTestRule.setContent {
            MaterialTheme { MoreScreen(onDestinationClick = { clicked += it }, onLinkClick = {}) }
        }

        listOf(
            "Mass times" to MoreDestination.MassTimes,
            "Mass Mode" to MoreDestination.MassMode,
            "Liturgy" to MoreDestination.DailyLiturgy,
            "Saints" to MoreDestination.Saints,
            "Community" to MoreDestination.Community,
            "Calendar" to MoreDestination.Calendar,
            "Prayer" to MoreDestination.PrayerHub,
            "Tithes" to MoreDestination.Tithes,
            "Contact" to MoreDestination.Contact,
            "Groups" to MoreDestination.Groups,
            "Catechesis" to MoreDestination.Catechesis,
            "Confirmation" to MoreDestination.Confirmation,
            "Novenas" to MoreDestination.Novenas,
            "Rosary" to MoreDestination.Rosary,
            "Examination" to MoreDestination.Exam,
        ).forEach { (label, destination) ->
            composeTestRule.onNodeWithText(label).performScrollTo().performClick()
            assertThat(clicked.last()).isEqualTo(destination)
        }
        assertThat(clicked).hasSize(MoreDestination.entries.size)
    }

    @Test
    public fun showsTheParishOfficeHoursAndPhone() {
        composeTestRule.setContent {
            MaterialTheme { MoreScreen(onDestinationClick = {}, onLinkClick = {}) }
        }

        composeTestRule.onNodeWithText("Tuesday to Friday, 14h–18h · Saturday, 08h–12h")
            .performScrollTo()
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("(11) 5928-9576").performScrollTo().assertIsDisplayed()
    }

    @Test
    public fun contactsButtonOpensTheContactDestination() {
        var clicked: MoreDestination? = null
        composeTestRule.setContent {
            MaterialTheme { MoreScreen(onDestinationClick = { clicked = it }, onLinkClick = {}) }
        }

        composeTestRule.onNodeWithText("See contacts and address").performScrollTo().performClick()

        assertThat(clicked).isEqualTo(MoreDestination.Contact)
    }
}
