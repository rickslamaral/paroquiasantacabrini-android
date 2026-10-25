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
import br.com.paroquiasantacabrini.app.feature.home.ui.model.AnnouncementUiModel
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeDestination
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeUiState
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35], qualifiers = "w411dp-h1600dp")
public class HomeScreenTest {

    @get:Rule
    public val composeTestRule = createComposeRule()

    @Test
    public fun headerShowsDefaultParishNameAndForwardsSwitchParishIntent() {
        var switchClicks = 0
        setHomeContent(onSwitchParishClick = { switchClicks += 1 })

        composeTestRule.onNodeWithText("Paróquia Santa Cabrini").assertIsDisplayed()
        composeTestRule.onNodeWithText("Switch parish").performClick()

        assertThat(switchClicks).isEqualTo(1)
    }

    @Test
    public fun headerShowsActiveParishName() {
        setHomeContent(state = HomeUiState(parishName = "Paróquia São José"))

        composeTestRule.onNodeWithText("Paróquia São José").assertIsDisplayed()
    }

    @Test
    public fun quickAccessCardsForwardTheirDestination() {
        val clicked = mutableListOf<HomeDestination>()
        setHomeContent(onQuickAccessClick = { clicked += it })

        composeTestRule.onNodeWithText("Daily Liturgy").performClick()
        composeTestRule.onNodeWithText("Mass Times").performClick()
        composeTestRule.onNodeWithText("Prayer Requests").performClick()
        composeTestRule.onNodeWithText("Tithes and Offerings (PIX)").performClick()

        assertThat(clicked).containsExactly(
            HomeDestination.DailyLiturgy,
            HomeDestination.MassSchedule,
            HomeDestination.PrayerRequests,
            HomeDestination.TithesAndOfferings,
        ).inOrder()
    }

    @Test
    public fun emptyAnnouncementsShowPlaceholder() {
        setHomeContent()

        composeTestRule.onNodeWithText("No announcements right now.").assertIsDisplayed()
    }

    @Test
    public fun announcementsAreDisplayed() {
        setHomeContent(
            state = HomeUiState(
                announcements = listOf(
                    AnnouncementUiModel(
                        id = "1",
                        title = "Festa da padroeira",
                        message = "Missa solene às 19h.",
                        dateLabel = "13 de novembro",
                    ),
                ),
            ),
        )

        composeTestRule.onNodeWithText("Festa da padroeira").assertIsDisplayed()
        composeTestRule.onNodeWithText("Missa solene às 19h.").assertIsDisplayed()
        composeTestRule.onNodeWithText("No announcements right now.").assertDoesNotExist()
    }

    private fun setHomeContent(
        state: HomeUiState = HomeUiState(),
        onQuickAccessClick: (HomeDestination) -> Unit = {},
        onSwitchParishClick: () -> Unit = {},
    ) {
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    state = state,
                    onQuickAccessClick = onQuickAccessClick,
                    onSwitchParishClick = onSwitchParishClick,
                )
            }
        }
    }
}
