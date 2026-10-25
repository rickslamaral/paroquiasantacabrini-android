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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.PageBanner
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaImages
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
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
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        ) {
            Column(
                modifier = Modifier.padding(TemplateSpacing.medium),
                verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
            ) {
                Text(
                    text = stringResource(R.string.feature_liturgy_daily_card_eyebrow),
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = stringResource(R.string.feature_liturgy_daily_card_title),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = stringResource(R.string.feature_liturgy_daily_card_note),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        SectionHeading(text = stringResource(R.string.feature_liturgy_daily_guide_title))
        GuideStep(
            title = stringResource(R.string.feature_liturgy_daily_step_1_title),
            text = stringResource(R.string.feature_liturgy_daily_step_1_text),
        )
        GuideStep(
            title = stringResource(R.string.feature_liturgy_daily_step_2_title),
            text = null,
        )
        Callout(text = stringResource(R.string.feature_liturgy_daily_step_2_text))
        GuideStep(
            title = stringResource(R.string.feature_liturgy_daily_step_3_title),
            text = stringResource(R.string.feature_liturgy_daily_step_3_text),
        )
        Button(
            onClick = onPrayerRequestClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.feature_liturgy_daily_prayer_request))
        }
    }
}

@Composable
private fun GuideStep(
    title: String,
    text: String?,
) {
    Column(verticalArrangement = Arrangement.spacedBy(TemplateSpacing.extraSmall)) {
        Text(text = title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        if (text != null) {
            Text(text = text, style = MaterialTheme.typography.bodyLarge)
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
