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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaProgress
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateButton
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateOutlinedButton
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaSerifFontFamily
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.prayer.R
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.GloriousMysteries
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.RosarySteps

@Composable
internal fun RosaryScreen(
    stepIndex: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val step = RosarySteps[stepIndex]
    ParoquiaPage(
        title = stringResource(R.string.feature_prayer_rosary_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_prayer_rosary_eyebrow),
        onBackClick = onBackClick,
    ) {
        ParoquiaCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(step.titleRes),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = stringResource(R.string.feature_prayer_rosary_progress, stepIndex + 1, RosarySteps.size),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            ParoquiaProgress(progress = (stepIndex + 1) / RosarySteps.size.toFloat())
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surfaceContainer,
        ) {
            Text(
                text = stringResource(step.textRes),
                modifier = Modifier.padding(PRAYER_PADDING),
                fontFamily = ParoquiaSerifFontFamily,
                fontSize = PRAYER_TEXT_SIZE,
                lineHeight = PRAYER_LINE_HEIGHT,
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.small)) {
            TemplateOutlinedButton(
                onClick = onPreviousClick,
                enabled = stepIndex > 0,
                modifier = Modifier.weight(1f),
            ) {
                Text(stringResource(R.string.feature_prayer_rosary_back))
            }
            TemplateButton(
                onClick = onNextClick,
                enabled = stepIndex < RosarySteps.lastIndex,
                modifier = Modifier.weight(1f),
            ) {
                Text(stringResource(R.string.feature_prayer_rosary_next))
            }
        }
        Text(
            text = stringResource(R.string.feature_prayer_rosary_hint),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        SectionHeading(text = stringResource(R.string.feature_prayer_rosary_mysteries_title))
        ParoquiaCard {
            GloriousMysteries.forEach { mystery ->
                Text(text = stringResource(mystery), style = MaterialTheme.typography.bodyMedium)
            }
        }
        Callout(text = stringResource(R.string.feature_prayer_rosary_disclaimer))
    }
}

@Preview(showBackground = true)
@Composable
private fun RosaryScreenPreview() {
    ParoquiaTheme {
        RosaryScreen(stepIndex = 0, onPreviousClick = {}, onNextClick = {}, onBackClick = {})
    }
}

private val PRAYER_PADDING = 20.dp
private val PRAYER_TEXT_SIZE = 15.sp
private val PRAYER_LINE_HEIGHT = 23.sp
