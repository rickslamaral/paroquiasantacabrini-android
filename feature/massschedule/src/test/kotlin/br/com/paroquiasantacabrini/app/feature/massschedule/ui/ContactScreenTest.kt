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
import androidx.compose.ui.test.onNodeWithContentDescription
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
@Config(sdk = [35], qualifiers = "w411dp-h2400dp")
public class ContactScreenTest {

    @get:Rule
    public val composeTestRule = createComposeRule()

    @Test
    public fun showsContactDetails() {
        setContactContent()

        composeTestRule.onNodeWithText("(11) 5928-9576").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Pe. Vinícius Henrique Maciel")
            .performScrollTo()
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Mariana").performScrollTo().assertIsDisplayed()
    }

    @Test
    public fun photosAreDescribedForScreenReaders() {
        setContactContent()

        composeTestRule.onNodeWithText("Our church").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            "Altar with a crucifix and the Blessed Sacrament exposed",
        ).assertExists()
    }

    @Test
    public fun contactActionsForwardMapPhoneWhatsappAndEmailUris() {
        val opened = mutableListOf<String>()
        setContactContent(onActionClick = { opened += it })

        composeTestRule.onNodeWithText("Open address on the map").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Call").performScrollTo().performClick()
        composeTestRule.onNodeWithText("WhatsApp").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Send e-mail").performScrollTo().performClick()

        assertThat(opened).hasSize(4)
        assertThat(opened[0]).startsWith("https://www.google.com/maps/search/?api=1&query=")
        assertThat(opened[1]).isEqualTo("tel:+551159289576")
        assertThat(opened[2]).isEqualTo("https://wa.me/551159289576")
        assertThat(opened[3]).isEqualTo("mailto:santafranciscaxaviercabrini@santoamaro.org.br")
    }

    @Test
    public fun socialChannelsForwardTheirUrl() {
        val opened = mutableListOf<String>()
        setContactContent(onActionClick = { opened += it })

        composeTestRule.onNodeWithText("Instagram").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Facebook").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Diocese").performScrollTo().performClick()

        assertThat(opened).containsExactly(
            "https://www.instagram.com/stacabrini/",
            "https://www.facebook.com/santafranciscaxaviercabrini",
            "https://diocesedesantoamaro.org.br/paroquias-da-diocese-de-santo-amaro/" +
                "paroquia-santa-francisca-xavier-cabrini/",
        ).inOrder()
    }

    private fun setContactContent(onActionClick: (String) -> Unit = {}) {
        composeTestRule.setContent {
            MaterialTheme {
                ContactScreen(
                    contact = MassScheduleUiState.forParish().contact,
                    onActionClick = onActionClick,
                )
            }
        }
    }
}
