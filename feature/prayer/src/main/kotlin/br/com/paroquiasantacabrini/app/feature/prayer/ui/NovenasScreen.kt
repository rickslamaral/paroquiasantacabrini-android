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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.FeatureCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.PageBanner
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaImages
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaProgress
import br.com.paroquiasantacabrini.app.core.designsystem.component.Pill
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateButton
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.core.designsystem.theme.extraColors
import br.com.paroquiasantacabrini.app.feature.prayer.R
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.NovenaProgress

@Composable
internal fun NovenasScreen(
    day: Int,
    feedbackRes: Int?,
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ParoquiaPage(
        title = stringResource(R.string.feature_prayer_novena_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_prayer_novena_eyebrow),
        onBackClick = onBackClick,
    ) {
        PageBanner(
            imageRes = ParoquiaImages.CabriniMission,
            contentDescription = stringResource(R.string.feature_prayer_novena_banner_description),
        )
        FeatureCard(
            eyebrow = stringResource(R.string.feature_prayer_novena_card_eyebrow),
            title = stringResource(R.string.feature_prayer_novena_card_title),
            text = stringResource(R.string.feature_prayer_novena_card_text),
            containerColor = MaterialTheme.extraColors.rose,
        ) {
            Pill(
                text = stringResource(R.string.feature_prayer_novena_day, day, NovenaProgress.LAST_DAY),
                modifier = Modifier.padding(top = TemplateSpacing.small),
            )
            ParoquiaProgress(progress = day / NovenaProgress.LAST_DAY.toFloat())
            TemplateButton(onClick = onContinueClick, modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(
                        if (day < NovenaProgress.LAST_DAY) {
                            R.string.feature_prayer_novena_continue
                        } else {
                            R.string.feature_prayer_novena_restart
                        },
                    ),
                )
            }
        }
        // Always composed so screen readers announce the message when it appears.
        Text(
            text = feedbackRes?.let { stringResource(it) }.orEmpty(),
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Callout(text = stringResource(R.string.feature_prayer_novena_disclaimer))
    }
}

@Preview(showBackground = true)
@Composable
private fun NovenasScreenPreview() {
    ParoquiaTheme {
        NovenasScreen(day = 3, feedbackRes = null, onContinueClick = {}, onBackClick = {})
    }
}
