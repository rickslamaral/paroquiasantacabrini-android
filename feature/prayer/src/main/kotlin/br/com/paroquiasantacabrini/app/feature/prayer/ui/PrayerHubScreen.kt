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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.paroquiasantacabrini.app.core.designsystem.component.FeatureBanner
import br.com.paroquiasantacabrini.app.core.designsystem.component.NavCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaImages
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaTonalButton
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.feature.prayer.R

@Composable
internal fun PrayerHubScreen(
    onRosaryClick: () -> Unit,
    onNovenasClick: () -> Unit,
    onPrayerRequestClick: () -> Unit,
    onDailyLiturgyClick: () -> Unit,
    onExamClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ParoquiaPage(
        title = stringResource(R.string.feature_prayer_hub_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_prayer_hub_eyebrow),
    ) {
        Text(
            text = stringResource(R.string.feature_prayer_hub_intro),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        NavCard(
            title = stringResource(R.string.feature_prayer_hub_rosary_title),
            description = stringResource(R.string.feature_prayer_hub_rosary_text),
            emoji = "◉",
            onClick = onRosaryClick,
        )
        NavCard(
            title = stringResource(R.string.feature_prayer_hub_novenas_title),
            description = stringResource(R.string.feature_prayer_hub_novenas_text),
            emoji = "✧",
            onClick = onNovenasClick,
        )
        NavCard(
            title = stringResource(R.string.feature_prayer_hub_requests_title),
            description = stringResource(R.string.feature_prayer_hub_requests_text),
            emoji = "♡",
            onClick = onPrayerRequestClick,
        )
        NavCard(
            title = stringResource(R.string.feature_prayer_hub_liturgy_title),
            description = stringResource(R.string.feature_prayer_hub_liturgy_text),
            emoji = "▤",
            onClick = onDailyLiturgyClick,
        )
        FeatureBanner(
            imageRes = ParoquiaImages.RosaryBanner,
            eyebrow = stringResource(R.string.feature_prayer_hub_banner_eyebrow),
            title = stringResource(R.string.feature_prayer_hub_banner_title),
            text = stringResource(R.string.feature_prayer_hub_banner_text),
            onClick = onRosaryClick,
        )
        SectionHeading(text = stringResource(R.string.feature_prayer_hub_exam_title))
        ParoquiaCard {
            Text(
                text = stringResource(R.string.feature_prayer_hub_exam_text),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            ParoquiaTonalButton(onClick = onExamClick, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.feature_prayer_hub_exam_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrayerHubScreenPreview() {
    ParoquiaTheme {
        PrayerHubScreen(
            onRosaryClick = {},
            onNovenasClick = {},
            onPrayerRequestClick = {},
            onDailyLiturgyClick = {},
            onExamClick = {},
        )
    }
}
