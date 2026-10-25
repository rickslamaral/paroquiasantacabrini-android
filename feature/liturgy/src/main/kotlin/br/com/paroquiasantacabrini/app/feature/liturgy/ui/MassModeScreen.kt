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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.PageBanner
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaImages
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaProgress
import br.com.paroquiasantacabrini.app.core.designsystem.component.Pill
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateButton
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateOutlinedButton
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.core.designsystem.theme.extraColors
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
            TextSizeButton(
                label = stringResource(R.string.feature_liturgy_mass_text_smaller_label),
                description = stringResource(R.string.feature_liturgy_mass_text_smaller),
                enabled = state.canShrinkText,
                onClick = onSmallerTextClick,
            )
            TextSizeButton(
                label = stringResource(R.string.feature_liturgy_mass_text_larger_label),
                description = stringResource(R.string.feature_liturgy_mass_text_larger),
                enabled = state.canGrowText,
                onClick = onLargerTextClick,
            )
        },
    ) {
        PageBanner(
            imageRes = ParoquiaImages.MassBanner,
            contentDescription = stringResource(R.string.feature_liturgy_mass_banner_description),
        )
        CurrentPartCard(state = state)
        Row(verticalAlignment = Alignment.CenterVertically) {
            SectionHeading(
                text = stringResource(R.string.feature_liturgy_mass_parts_title),
                modifier = Modifier.weight(1f),
            )
            Pill(text = stringResource(R.string.feature_liturgy_mass_parts_hint))
        }
        MassParts.forEachIndexed { index, part ->
            PartRow(
                number = index + 1,
                title = stringResource(part.titleRes),
                isCurrent = index == state.partIndex,
                onClick = { onPartClick(index) },
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.small)) {
            TemplateOutlinedButton(
                onClick = onPreviousClick,
                enabled = state.canGoBack,
                modifier = Modifier.weight(1f),
            ) {
                Text(stringResource(R.string.feature_liturgy_mass_previous))
            }
            TemplateButton(
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

/** A small bordered A-/A+ button from the prototype's `mass-controls`. */
@Composable
private fun TextSizeButton(
    label: String,
    description: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
            .clip(MaterialTheme.shapes.extraSmall)
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            .semantics { contentDescription = description },
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraSmall,
            color = colors.surfaceContainerLowest,
            border = BorderStroke(1.dp, colors.outlineVariant),
        ) {
            Text(
                text = label,
                modifier = Modifier
                    .padding(horizontal = 9.dp, vertical = 7.dp)
                    .clearAndSetSemantics {},
                style = MaterialTheme.typography.labelLarge,
                color = if (enabled) colors.primary else colors.onSurfaceVariant.copy(alpha = DISABLED_ALPHA),
            )
        }
    }
}

@Composable
private fun CurrentPartCard(state: MassModeState) {
    val part = MassParts[state.partIndex]
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(part.titleRes),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = stringResource(
                        R.string.feature_liturgy_mass_part_progress,
                        state.partIndex + 1,
                        MassParts.size,
                    ),
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            ParoquiaProgress(
                progress = (state.partIndex + 1) / MassParts.size.toFloat(),
                modifier = Modifier.padding(vertical = 10.dp),
            )
            Text(
                text = stringResource(part.textRes),
                modifier = Modifier.padding(top = 2.dp),
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
    val colors = MaterialTheme.colorScheme
    val status = stringResource(
        if (isCurrent) R.string.feature_liturgy_mass_part_current else R.string.feature_liturgy_mass_part_open,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(if (isCurrent) colors.primaryContainer else colors.surfaceContainerLowest)
            .border(
                BorderStroke(1.dp, if (isCurrent) colors.primary.copy(alpha = CURRENT_BORDER_ALPHA) else colors.outlineVariant),
                MaterialTheme.shapes.medium,
            )
            .clickable(role = Role.Button, onClick = onClick)
            .semantics(mergeDescendants = true) { selected = isCurrent }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(MaterialTheme.extraColors.cream),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.labelMedium,
                color = colors.primary,
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp))
            Text(
                text = status,
                modifier = Modifier.padding(top = 3.dp),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = null, letterSpacing = 0.sp),
                color = colors.onSurfaceVariant,
            )
        }
        Text(
            text = "›",
            modifier = Modifier.clearAndSetSemantics {},
            style = MaterialTheme.typography.headlineSmall,
            color = colors.primary,
        )
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

private const val LINE_HEIGHT_FACTOR = 1.65f
private const val DISABLED_ALPHA = 0.5f
private const val CURRENT_BORDER_ALPHA = 0.3f
