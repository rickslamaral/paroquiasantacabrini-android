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

package br.com.paroquiasantacabrini.app.feature.home.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.home.R
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeDestination

internal data class QuickAccessItem(
    internal val destination: HomeDestination,
    internal val emoji: String,
    @get:StringRes internal val labelRes: Int,
    internal val isPrimary: Boolean,
)

internal val QuickAccessItems: List<QuickAccessItem> = listOf(
    QuickAccessItem(HomeDestination.DailyLiturgy, "📖", R.string.feature_home_daily_liturgy, isPrimary = true),
    QuickAccessItem(HomeDestination.MassSchedule, "🕒", R.string.feature_home_mass_schedule, isPrimary = false),
    QuickAccessItem(HomeDestination.PrayerRequests, "🙏", R.string.feature_home_prayer_requests, isPrimary = false),
    QuickAccessItem(
        HomeDestination.TithesAndOfferings,
        "💳",
        R.string.feature_home_tithes_and_offerings,
        isPrimary = true,
    ),
)

@Composable
internal fun QuickAccessCard(
    item: QuickAccessItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    val (container, content) = if (item.isPrimary) {
        colors.primaryContainer to colors.onPrimaryContainer
    } else {
        colors.secondaryContainer to colors.onSecondaryContainer
    }

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = MIN_CARD_HEIGHT),
        colors = CardDefaults.cardColors(containerColor = container, contentColor = content),
    ) {
        Column(
            modifier = Modifier.padding(TemplateSpacing.medium),
            verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
        ) {
            // The emoji is decorative; the label alone is announced by screen readers.
            Text(
                text = item.emoji,
                modifier = Modifier.clearAndSetSemantics {},
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = stringResource(item.labelRes),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

private val MIN_CARD_HEIGHT = 120.dp
