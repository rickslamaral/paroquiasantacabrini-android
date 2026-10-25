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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.PageBanner
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaImages
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.Pill
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateButton
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.core.designsystem.theme.extraColors
import br.com.paroquiasantacabrini.app.feature.liturgy.R

@Composable
internal fun DailyLiturgyScreen(
    onPrayerRequestClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ParoquiaPage(
        title = stringResource(R.string.feature_liturgy_daily_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_liturgy_daily_eyebrow),
        onBackClick = onBackClick,
    ) {
        PageBanner(
            imageRes = ParoquiaImages.MassBanner,
            contentDescription = stringResource(R.string.feature_liturgy_daily_banner_description),
        )
        ParoquiaCard(containerColor = MaterialTheme.extraColors.cream, bordered = false) {
            Text(
                text = stringResource(R.string.feature_liturgy_daily_card_eyebrow).uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
            )
            Text(
                text = stringResource(R.string.feature_liturgy_daily_card_title),
                modifier = Modifier.semantics { heading() },
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = stringResource(R.string.feature_liturgy_daily_card_note),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SectionHeading(
                text = stringResource(R.string.feature_liturgy_daily_guide_title),
                modifier = Modifier.weight(1f),
            )
            Pill(text = stringResource(R.string.feature_liturgy_daily_guide_badge))
        }
        ParoquiaCard {
            GuideStep(
                title = stringResource(R.string.feature_liturgy_daily_step_1_title),
                text = stringResource(R.string.feature_liturgy_daily_step_1_text),
            )
            GuideStep(title = stringResource(R.string.feature_liturgy_daily_step_2_title), text = null)
            Callout(text = stringResource(R.string.feature_liturgy_daily_step_2_text))
            GuideStep(
                title = stringResource(R.string.feature_liturgy_daily_step_3_title),
                text = stringResource(R.string.feature_liturgy_daily_step_3_text),
            )
        }
        TemplateButton(onClick = onPrayerRequestClick, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.feature_liturgy_daily_prayer_request))
        }
    }
}

@Composable
private fun GuideStep(
    title: String,
    text: String?,
) {
    Column(
        modifier = Modifier.padding(top = TemplateSpacing.extraSmall),
        verticalArrangement = Arrangement.spacedBy(TemplateSpacing.extraSmall),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(fontSize = STEP_TITLE_SIZE),
            color = MaterialTheme.colorScheme.primary,
        )
        if (text != null) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DailyLiturgyScreenPreview() {
    ParoquiaTheme {
        DailyLiturgyScreen(onPrayerRequestClick = {}, onBackClick = {})
    }
}

private val STEP_TITLE_SIZE = 12.sp
