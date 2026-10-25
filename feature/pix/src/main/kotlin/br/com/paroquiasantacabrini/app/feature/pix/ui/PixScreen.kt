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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateBackground
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateButton
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.pix.R
import br.com.paroquiasantacabrini.app.feature.pix.ui.components.PixDetails
import br.com.paroquiasantacabrini.app.feature.pix.ui.components.QrCode
import br.com.paroquiasantacabrini.app.feature.pix.ui.model.PixCopyTarget
import br.com.paroquiasantacabrini.app.feature.pix.ui.model.PixUiState

@Composable
internal fun PixScreen(
    state: PixUiState,
    copiedTarget: PixCopyTarget?,
    onCopyKeyClick: () -> Unit,
    onCopyPayloadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TemplateBackground(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = MAX_CONTENT_WIDTH)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(TemplateSpacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(TemplateSpacing.medium),
            ) {
                Text(
                    text = stringResource(R.string.feature_pix_title),
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { heading() },
                    style = MaterialTheme.typography.headlineMedium,
                )
                Text(
                    text = stringResource(R.string.feature_pix_intro),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                )
                QrCode(
                    matrix = state.qrCode,
                    description = stringResource(R.string.feature_pix_qr_description),
                )
                Text(
                    text = stringResource(R.string.feature_pix_amount_hint),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = stringResource(R.string.feature_pix_disclaimer),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                PixDetails(state = state)
                TemplateButton(
                    onClick = onCopyKeyClick,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(R.string.feature_pix_copy_key))
                }
                OutlinedButton(
                    onClick = onCopyPayloadClick,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(R.string.feature_pix_copy_code))
                }
                CopyFeedback(copiedTarget = copiedTarget)
            }
        }
    }
}

@Composable
private fun CopyFeedback(
    copiedTarget: PixCopyTarget?,
    modifier: Modifier = Modifier,
) {
    val message = when (copiedTarget) {
        PixCopyTarget.Key -> stringResource(R.string.feature_pix_copied_key)
        PixCopyTarget.Payload -> stringResource(R.string.feature_pix_copied_code)
        null -> null
    }
    // Always composed so screen readers announce the message when it appears.
    Text(
        text = message.orEmpty(),
        modifier = modifier.semantics { liveRegion = LiveRegionMode.Polite },
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.primary,
    )
}

@Preview(showBackground = true)
@Composable
private fun PixScreenPreview() {
    ParoquiaTheme {
        PixScreen(
            state = PixUiState.forDonation(),
            copiedTarget = null,
            onCopyKeyClick = {},
            onCopyPayloadClick = {},
        )
    }
}

private val MAX_CONTENT_WIDTH = 560.dp
