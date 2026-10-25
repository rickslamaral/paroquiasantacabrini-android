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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.InfoRow
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateButton
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateOutlinedButton
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.feature.pix.R
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
    onBackClick: (() -> Unit)? = null,
) {
    ParoquiaPage(
        title = stringResource(R.string.feature_pix_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_pix_eyebrow),
        onBackClick = onBackClick,
    ) {
        Text(
            text = stringResource(R.string.feature_pix_intro),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        KeyCard(
            state = state,
            onCopyKeyClick = onCopyKeyClick,
            onCopyPayloadClick = onCopyPayloadClick,
        )
        CopyFeedback(copiedTarget = copiedTarget)
        ParoquiaCard {
            Column {
                InfoRow(
                    glyph = "✓",
                    title = stringResource(R.string.feature_pix_receiver_label),
                    text = state.receiverName,
                )
                InfoRow(
                    glyph = "▣",
                    title = stringResource(R.string.feature_pix_institution_label),
                    text = state.institution,
                    showDivider = false,
                )
            }
        }
        SectionHeading(text = stringResource(R.string.feature_pix_qr_title))
        ParoquiaCard {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                QrCode(
                    matrix = state.qrCode,
                    description = stringResource(R.string.feature_pix_qr_description),
                    modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                )
                Text(
                    text = stringResource(R.string.feature_pix_amount_hint),
                    modifier = Modifier.padding(top = 12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        Callout(text = stringResource(R.string.feature_pix_disclaimer))
    }
}

/** The sage card with the key in a dashed box and the copy actions (`copy-key`). */
@Composable
private fun KeyCard(
    state: PixUiState,
    onCopyKeyClick: () -> Unit,
    onCopyPayloadClick: () -> Unit,
) {
    ParoquiaCard(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        bordered = false,
    ) {
        Text(
            text = stringResource(R.string.feature_pix_key_eyebrow).uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
        )
        DashedKeyBox(text = state.pixKeyFormatted)
        TemplateButton(onClick = onCopyKeyClick, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.feature_pix_copy_key))
        }
        TemplateOutlinedButton(onClick = onCopyPayloadClick, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.feature_pix_copy_code))
        }
    }
}

@Composable
private fun DashedKeyBox(text: String) {
    val stroke = MaterialTheme.colorScheme.outline
    val fill = MaterialTheme.colorScheme.surfaceContainer
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(KEY_CORNER))
            .padding(1.dp),
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawRoundRect(color = fill, cornerRadius = CornerRadius(KEY_CORNER.toPx()))
            drawRoundRect(
                color = stroke,
                cornerRadius = CornerRadius(KEY_CORNER.toPx()),
                style = Stroke(width = 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(DASH, DASH))),
            )
        }
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            letterSpacing = 0.5.sp,
        )
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
            onBackClick = {},
        )
    }
}

private val KEY_CORNER = 14.dp
private const val DASH = 10f
