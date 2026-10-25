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
import androidx.compose.ui.test.junit4.v2.createComposeRule
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
public class DailyLiturgyScreenTest {

    @get:Rule
    public val composeTestRule = createComposeRule()

    @Test
    public fun explainsThatTheContentIsDemonstrativeAndHasNoOfficialReadings() {
        setContent()

        composeTestRule.onNodeWithText("Daily liturgy").assertIsDisplayed()
        composeTestRule.onNodeWithText(
            "Demonstration content. The official readings must be consulted in the liturgical " +
                "publications of the Church.",
        ).performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText(
            "The readings of the day will appear here once the parish connects an authorized " +
                "liturgical source.",
        ).performScrollTo().assertIsDisplayed()
    }

    @Test
    public fun prayerRequestButtonForwardsIntent() {
        var clicks = 0
        setContent(onPrayerRequestClick = { clicks += 1 })

        composeTestRule.onNodeWithText("Make a prayer request").performScrollTo().performClick()

        assertThat(clicks).isEqualTo(1)
    }

    private fun setContent(onPrayerRequestClick: () -> Unit = {}) {
        composeTestRule.setContent {
            MaterialTheme {
                DailyLiturgyRoute(onPrayerRequestClick = onPrayerRequestClick, onBackClick = {})
            }
        }
    }
}
