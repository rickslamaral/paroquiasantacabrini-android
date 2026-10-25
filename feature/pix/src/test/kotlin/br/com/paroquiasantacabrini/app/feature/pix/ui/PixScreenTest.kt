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

package br.com.paroquiasantacabrini.app.feature.pix.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.paroquiasantacabrini.app.feature.pix.ui.model.PixCopyTarget
import br.com.paroquiasantacabrini.app.feature.pix.ui.model.PixUiState
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35], qualifiers = "w411dp-h1600dp")
public class PixScreenTest {

    @get:Rule
    public val composeTestRule = createComposeRule()

    @Test
    public fun showsReceiverBankAndFormattedCnpjKey() {
        setPixContent()

        composeTestRule.onNodeWithText("Tithes and Offerings (PIX)").assertIsDisplayed()
        composeTestRule.onNodeWithText("PAROQUIA SANTA FRANCISCA").assertIsDisplayed()
        composeTestRule.onNodeWithText("Itaú Unibanco S.A.").assertIsDisplayed()
        composeTestRule.onNodeWithText("60.909.843/0082-00").assertIsDisplayed()
    }

    @Test
    public fun copyButtonsForwardUserIntent() {
        var keyClicks = 0
        var payloadClicks = 0
        setPixContent(
            onCopyKeyClick = { keyClicks += 1 },
            onCopyPayloadClick = { payloadClicks += 1 },
        )

        composeTestRule.onNodeWithText("Copy Pix key").performClick()
        composeTestRule.onNodeWithText("Copy Pix copy-and-paste code").performClick()

        assertThat(keyClicks).isEqualTo(1)
        assertThat(payloadClicks).isEqualTo(1)
    }

    @Test
    public fun copiedFeedbackIsDisplayedForTheCopiedItem() {
        setPixContent(copiedTarget = PixCopyTarget.Payload)

        composeTestRule.onNodeWithText("Pix code copied.").assertIsDisplayed()
    }

    private fun setPixContent(
        copiedTarget: PixCopyTarget? = null,
        onCopyKeyClick: () -> Unit = {},
        onCopyPayloadClick: () -> Unit = {},
    ) {
        composeTestRule.setContent {
            MaterialTheme {
                PixScreen(
                    state = PixUiState.forDonation(),
                    copiedTarget = copiedTarget,
                    onCopyKeyClick = onCopyKeyClick,
                    onCopyPayloadClick = onCopyPayloadClick,
                )
            }
        }
    }
}
