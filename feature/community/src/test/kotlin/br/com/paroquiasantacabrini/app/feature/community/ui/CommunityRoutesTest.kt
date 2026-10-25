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

package br.com.paroquiasantacabrini.app.feature.community.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
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
import java.time.LocalDate

private class RecordingUriHandler : UriHandler {
    public val opened: MutableList<String> = mutableListOf()

    public override fun openUri(uri: String) {
        opened += uri
    }
}

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35], qualifiers = "w411dp-h2000dp")
public class CommunityRoutesTest {

    @get:Rule
    public val composeTestRule = createComposeRule()

    private val handler = RecordingUriHandler()

    private fun setContent(content: @androidx.compose.runtime.Composable () -> Unit) {
        composeTestRule.setContent {
            MaterialTheme {
                CompositionLocalProvider(LocalUriHandler provides handler) { content() }
            }
        }
    }

    @Test
    public fun hubForwardsEachDestinationAndOpensWhatsApp() {
        val clicked = mutableListOf<String>()
        setContent {
            CommunityHubRoute(
                onGroupsClick = { clicked += "groups" },
                onCatechesisClick = { clicked += "catechesis" },
                onConfirmationClick = { clicked += "confirmation" },
                onBackClick = {},
            )
        }

        composeTestRule.onNodeWithText("Prayer groups").performClick()
        composeTestRule.onNodeWithText("Catechesis").performClick()
        composeTestRule.onNodeWithText("Confirmation").performClick()
        composeTestRule.onNodeWithText("Talk to the parish office").performScrollTo().performClick()

        assertThat(clicked).containsExactly("groups", "catechesis", "confirmation").inOrder()
        assertThat(handler.opened).containsExactly("https://wa.me/551159289576")
    }

    @Test
    public fun groupsPageDoesNotInventMeetings() {
        setContent { GroupsRoute(onBackClick = {}) }

        composeTestRule.onNodeWithText(
            "Group names and times were not provided. This version avoids showing invented " +
                "meetings as the real schedule.",
        ).performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Ask on WhatsApp").performScrollTo().performClick()
        assertThat(handler.opened).containsExactly("https://wa.me/551159289576")
    }

    @Test
    public fun catechesisProtectsMinorsAndConfirmationPointsToTheOffice() {
        setContent { CatechesisRoute(onBackClick = {}) }
        composeTestRule.onNodeWithText(
            "To protect children and teenagers, this app does not show names, contacts or participant data.",
        ).performScrollTo().assertIsDisplayed()
    }

    @Test
    public fun confirmationStatesThatDatesAreNotConfirmed() {
        setContent { ConfirmationRoute(onBackClick = {}) }
        composeTestRule.onNodeWithText("Meeting dates and enrollment depend on the parish's official schedule.")
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    public fun noticesListsTheThreeRegularAnnouncementsAndTheRealtimeDisclaimer() {
        setContent { NoticesRoute(onBackClick = {}) }

        composeTestRule.onNodeWithText("Adoration of the Blessed Sacrament").assertIsDisplayed()
        composeTestRule.onNodeWithText("Parish office hours").assertIsDisplayed()
        composeTestRule.onNodeWithText("Scheduling and times").assertIsDisplayed()
        composeTestRule.onNodeWithText(
            "This version does not publish announcements in real time. Check the official channels for news.",
        ).performScrollTo().assertIsDisplayed()
    }

    @Test
    public fun calendarShowsTheSelectedDaysRegularTimes() {
        // 2026-10-25 is a Sunday: three masses and confessions.
        setContent { CalendarRoute(today = LocalDate.of(2026, 10, 25), onAllMassTimesClick = {}) }

        composeTestRule.onNodeWithText("October 2026").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sunday, 25 October").assertIsDisplayed()
        assertThat(composeTestRule.onAllNodesWithText("Holy Mass").fetchSemanticsNodes()).hasSize(3)
        composeTestRule.onNodeWithText("After the 08h and 10h30 Masses").assertIsDisplayed()
    }

    @Test
    public fun selectingAMondayShowsTheEmptyState() {
        setContent { CalendarRoute(today = LocalDate.of(2026, 10, 25), onAllMassTimesClick = {}) }

        composeTestRule.onNodeWithContentDescription("Monday, October 26, 2026", substring = true).performClick()

        composeTestRule.onNodeWithText("No regular times were provided for this date.").assertIsDisplayed()
    }

    @Test
    public fun nextAndPreviousMonthChangeTheTitleAndSelectTheFirstDay() {
        setContent { CalendarRoute(today = LocalDate.of(2026, 10, 25), onAllMassTimesClick = {}) }

        composeTestRule.onNodeWithContentDescription("Next month").performClick()
        composeTestRule.onNodeWithText("November 2026").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sunday, 1 November").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Previous month").performClick()
        composeTestRule.onNodeWithText("October 2026").assertIsDisplayed()
    }

    @Test
    public fun allMassTimesButtonForwardsIntent() {
        var clicks = 0
        setContent {
            CalendarRoute(today = LocalDate.of(2026, 10, 25), onAllMassTimesClick = { clicks += 1 })
        }

        composeTestRule.onNodeWithText("See all Mass times").performScrollTo().performClick()

        assertThat(clicks).isEqualTo(1)
    }
}
