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

package br.com.paroquiasantacabrini.app.tests.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.paroquiasantacabrini.app.home.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
public class HomeJourneyTest {

    @get:Rule
    public val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    public fun userSeesDashboardAndOpensQuickAccessDestination() {
        composeRule.onNodeWithText("Paróquia Santa Cabrini").assertIsDisplayed()
        composeRule.onNodeWithText("Mass Times").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("Prayer Requests").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("Tithes and Offerings (PIX)").performScrollTo().assertIsDisplayed()

        composeRule.onNodeWithText("Switch parish").performScrollTo().performClick()

        composeRule.onNodeWithText("Choosing another parish is not available yet.", substring = true).assertIsDisplayed()
    }
}
