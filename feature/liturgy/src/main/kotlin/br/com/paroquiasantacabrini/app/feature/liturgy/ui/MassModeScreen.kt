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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.PageBanner
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaImages
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.liturgy.R
import br.com.paroquiasantacabrini.app.feature.liturgy.ui.model.MassModeState
import br.com.paroquiasantacabrini.app.feature.liturgy.ui.model.MassParts

@Composable
internal fun MassModeScreen(
    state: MassModeState,
    onPartClick: (Int) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onSmallerTextClick: () -> Unit,
    onLargerTextClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ParoquiaPage(
        title = stringResource(R.string.feature_liturgy_mass_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_liturgy_mass_eyebrow),
        onBackClick = onBackClick,
        actions = {
            val smallerDescription = stringResource(R.string.feature_liturgy_mass_text_smaller)
            val largerDescription = stringResource(R.string.feature_liturgy_mass_text_larger)
            TextButton(
                onClick = onSmallerTextClick,
                modifier = Modifier.semantics { contentDescription = smallerDescription },
                enabled = state.canShrinkText,
            ) {
                Text(stringResource(R.string.feature_liturgy_mass_text_smaller_label))
            }
            TextButton(
                onClick = onLargerTextClick,
                modifier = Modifier.semantics { contentDescription = largerDescription },
                enabled = state.canGrowText,
            ) {
                Text(stringResource(R.string.feature_liturgy_mass_text_larger_label))
            }
        },
    ) {
        PageBanner(
            imageRes = ParoquiaImages.MassBanner,
            contentDescription = stringResource(R.string.feature_liturgy_mass_banner_description),
        )
        CurrentPartCard(state = state)
        SectionHeading(text = stringResource(R.string.feature_liturgy_mass_parts_title))
        MassParts.forEachIndexed { index, part ->
            PartRow(
                number = index + 1,
                title = stringResource(part.titleRes),
                isCurrent = index == state.partIndex,
                onClick = { onPartClick(index) },
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.small)) {
            OutlinedButton(
                onClick = onPreviousClick,
                enabled = state.canGoBack,
                modifier = Modifier.weight(1f),
            ) {
                Text(stringResource(R.string.feature_liturgy_mass_previous))
            }
            Button(
                onClick = onNextClick,
                enabled = state.canGoForward,
                modifier = Modifier.weight(1f),
            ) {
                Text(stringResource(R.string.feature_liturgy_mass_next))
            }
        }
        Callout(text = stringResource(R.string.feature_liturgy_mass_disclaimer))
    }
}

@Composable
private fun CurrentPartCard(state: MassModeState) {
    val part = MassParts[state.partIndex]
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(
            modifier = Modifier.padding(TemplateSpacing.medium),
            verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
        ) {
            Text(
                text = stringResource(part.titleRes),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = stringResource(
                    R.string.feature_liturgy_mass_part_progress,
                    state.partIndex + 1,
                    MassParts.size,
                ),
                style = MaterialTheme.typography.labelMedium,
            )
            LinearProgressIndicator(
                progress = { (state.partIndex + 1) / MassParts.size.toFloat() },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = TRACK_ALPHA),
            )
            Text(
                text = stringResource(part.textRes),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = state.textSizeSp.sp,
                lineHeight = (state.textSizeSp * LINE_HEIGHT_FACTOR).sp,
            )
        }
    }
}

@Composable
private fun PartRow(
    number: Int,
    title: String,
    isCurrent: Boolean,
    onClick: () -> Unit,
) {
    val status = stringResource(
        if (isCurrent) R.string.feature_liturgy_mass_part_current else R.string.feature_liturgy_mass_part_open,
    )
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrent) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceContainerHigh
            },
        ),
    ) {
        Column(modifier = Modifier.padding(TemplateSpacing.medium)) {
            Text(
                text = stringResource(R.string.feature_liturgy_mass_part_row, number, title),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(text = status, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MassModeScreenPreview() {
    ParoquiaTheme {
        MassModeScreen(
            state = MassModeState(partIndex = 1),
            onPartClick = {},
            onPreviousClick = {},
            onNextClick = {},
            onSmallerTextClick = {},
            onLargerTextClick = {},
            onBackClick = {},
        )
    }
}

private const val LINE_HEIGHT_FACTOR = 1.5f
private const val TRACK_ALPHA = 0.2f
