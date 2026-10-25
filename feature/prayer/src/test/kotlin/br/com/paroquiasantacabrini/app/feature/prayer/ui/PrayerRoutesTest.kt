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

package br.com.paroquiasantacabrini.app.feature.prayer.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import br.com.paroquiasantacabrini.app.feature.prayer.ui.data.PrayerStore
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.SavedRequest
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

private class FakePrayerStore(
    public override var novenaDay: Int = 1,
    public var request: SavedRequest? = null,
) : PrayerStore {
    public override fun savedRequest(): SavedRequest? = request

    public override fun saveRequest(request: SavedRequest) {
        this.request = request
    }

    public override fun clearRequest() {
        request = null
    }
}

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35], qualifiers = "w411dp-h1600dp")
public class PrayerRoutesTest {

    @get:Rule
    public val composeTestRule = createComposeRule()

    @Test
    public fun hubForwardsEachDestination() {
        val clicked = mutableListOf<String>()
        composeTestRule.setContent {
            MaterialTheme {
                PrayerHubRoute(
                    onRosaryClick = { clicked += "rosary" },
                    onNovenasClick = { clicked += "novenas" },
                    onPrayerRequestClick = { clicked += "request" },
                    onDailyLiturgyClick = { clicked += "liturgy" },
                    onExamClick = { clicked += "exam" },
                )
            }
        }

        composeTestRule.onNodeWithText("Holy Rosary").performClick()
        composeTestRule.onNodeWithText("Novenas").performClick()
        composeTestRule.onNodeWithText("Prayer requests").performClick()
        composeTestRule.onNodeWithText("Daily liturgy").performClick()
        composeTestRule.onNodeWithText("Start reflection").performScrollTo().performClick()

        assertThat(clicked).containsExactly("rosary", "novenas", "request", "liturgy", "exam").inOrder()
    }

    @Test
    public fun rosaryWalksThroughAllSixSteps() {
        composeTestRule.setContent { MaterialTheme { RosaryRoute(onBackClick = {}) } }

        composeTestRule.onNodeWithText("Step 1 of 6").assertIsDisplayed()
        composeTestRule.onNodeWithText("‹ Back").assertIsNotEnabled()
        repeat(5) { composeTestRule.onNodeWithText("Next step ›").performClick() }

        composeTestRule.onNodeWithText("Step 6 of 6").assertIsDisplayed()
        composeTestRule.onNodeWithText("Next step ›").assertIsNotEnabled()
    }

    @Test
    public fun novenaAdvancesPersistsAndRestartsAfterTheNinthDay() {
        val store = FakePrayerStore(novenaDay = 8)
        composeTestRule.setContent { MaterialTheme { NovenasRoute(store = store, onBackClick = {}) } }

        composeTestRule.onNodeWithText("Day 8 of 9").assertIsDisplayed()
        composeTestRule.onNodeWithText("Continue prayer").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Day 9 of 9").assertIsDisplayed()
        assertThat(store.novenaDay).isEqualTo(9)

        composeTestRule.onNodeWithText("Restart novena").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Day 1 of 9").assertIsDisplayed()
        composeTestRule.onNodeWithText("Novena restarted.").assertIsDisplayed()
        assertThat(store.novenaDay).isEqualTo(1)
    }

    @Test
    public fun examShowsAPeacefulMessageWhenFinishedAndStoresNothing() {
        composeTestRule.setContent { MaterialTheme { ExamRoute(onBackClick = {}) } }

        composeTestRule.onNodeWithText("Finish reflection").performScrollTo().performClick()

        composeTestRule.onNodeWithText("May this moment of reflection bring you peace.").assertIsDisplayed()
    }

    @Test
    public fun emptyRequestShowsAnErrorAndSavesNothing() {
        val store = FakePrayerStore()
        composeTestRule.setContent { MaterialTheme { PrayerRequestRoute(store = store, onBackClick = {}) } }

        composeTestRule.onNodeWithText("Save intention on this device").performScrollTo().performClick()

        composeTestRule.onNodeWithText("Write your intention to save it.").assertIsDisplayed()
        assertThat(store.request).isNull()
    }

    @Test
    public fun requestWithoutAcknowledgementIsNotSaved() {
        val store = FakePrayerStore()
        composeTestRule.setContent { MaterialTheme { PrayerRequestRoute(store = store, onBackClick = {}) } }

        composeTestRule.onNode(hasSetTextAction()).performTextInput("For peace")
        composeTestRule.onNodeWithText("Save intention on this device").performScrollTo().performClick()

        composeTestRule.onNodeWithText("Confirm that you understand to save.").assertIsDisplayed()
        assertThat(store.request).isNull()
    }

    @Test
    public fun acknowledgedRequestIsSavedOnlyToTheDeviceStoreAndCanBeDeleted() {
        val store = FakePrayerStore()
        composeTestRule.setContent { MaterialTheme { PrayerRequestRoute(store = store, onBackClick = {}) } }

        composeTestRule.onNode(hasSetTextAction()).performTextInput("For peace")
        composeTestRule.onNodeWithText(
            "I understand this version does not send my request to the parish.",
        ).performScrollTo().performClick()
        composeTestRule.onNodeWithText("Save intention on this device").performScrollTo().performClick()

        assertThat(store.request?.text).isEqualTo("For peace")
        composeTestRule.onNodeWithText("Intention saved on this device.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Saved on this device").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText(
            "Your intention was saved only on this device. It was not sent to the parish.",
        ).performScrollTo().assertIsDisplayed()

        composeTestRule.onNodeWithText("Delete saved intention").performScrollTo().performClick()
        assertThat(store.request).isNull()
        composeTestRule.onNodeWithText("Saved intention deleted.").assertIsDisplayed()
    }
}
