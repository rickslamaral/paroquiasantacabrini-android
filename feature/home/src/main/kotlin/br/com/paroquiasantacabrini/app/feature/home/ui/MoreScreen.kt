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

package br.com.paroquiasantacabrini.app.feature.home.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateBackground
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.home.R
import br.com.paroquiasantacabrini.app.feature.home.ui.components.ParishLinkCard
import br.com.paroquiasantacabrini.app.feature.home.ui.components.ParishLinks
import br.com.paroquiasantacabrini.app.feature.home.ui.model.MoreDestination

private data class MoreTile(
    internal val destination: MoreDestination,
    internal val emoji: String,
    @get:StringRes internal val labelRes: Int,
)

private val MoreTiles = listOf(
    MoreTile(MoreDestination.MassTimes, "◷", R.string.feature_home_more_mass_times),
    MoreTile(MoreDestination.MassMode, "✝", R.string.feature_home_more_mass_mode),
    MoreTile(MoreDestination.DailyLiturgy, "▤", R.string.feature_home_more_liturgy),
    MoreTile(MoreDestination.Saints, "✧", R.string.feature_home_more_saints),
    MoreTile(MoreDestination.Community, "♧", R.string.feature_home_more_community),
    MoreTile(MoreDestination.Calendar, "▦", R.string.feature_home_more_calendar),
    MoreTile(MoreDestination.PrayerHub, "♡", R.string.feature_home_more_prayer),
    MoreTile(MoreDestination.Tithes, "＋", R.string.feature_home_more_tithes),
    MoreTile(MoreDestination.Contact, "⌖", R.string.feature_home_more_contact),
    MoreTile(MoreDestination.Groups, "♧", R.string.feature_home_more_groups),
    MoreTile(MoreDestination.Catechesis, "▤", R.string.feature_home_more_catechesis),
    MoreTile(MoreDestination.Confirmation, "✧", R.string.feature_home_more_confirmation),
    MoreTile(MoreDestination.Novenas, "☼", R.string.feature_home_more_novenas),
    MoreTile(MoreDestination.Rosary, "◉", R.string.feature_home_more_rosary),
    MoreTile(MoreDestination.Exam, "♡", R.string.feature_home_more_exam),
)

@Composable
internal fun MoreScreen(
    onDestinationClick: (MoreDestination) -> Unit,
    onLinkClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TemplateBackground(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
            contentAlignment = Alignment.TopCenter,
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = TILE_MIN_WIDTH),
                modifier = Modifier
                    .widthIn(max = MAX_CONTENT_WIDTH)
                    .fillMaxSize(),
                contentPadding = PaddingValues(TemplateSpacing.medium),
                horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
                verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
            ) {
                item(key = "title", span = { GridItemSpan(maxLineSpan) }) { MoreHeader() }
                items(items = MoreTiles, key = { it.destination }) { tile ->
                    MoreTileCard(tile = tile, onClick = { onDestinationClick(tile.destination) })
                }
                item(key = "secretary", span = { GridItemSpan(maxLineSpan) }) {
                    SecretaryCard(onContactsClick = { onDestinationClick(MoreDestination.Contact) })
                }
                items(
                    items = ParishLinks,
                    key = { it.url },
                    span = { GridItemSpan(maxLineSpan) },
                ) { link -> ParishLinkCard(link = link, onClick = { onLinkClick(link.url) }) }
            }
        }
    }
}

@Composable
private fun MoreHeader() {
    Column(modifier = Modifier.padding(bottom = TemplateSpacing.small)) {
        Text(
            text = stringResource(R.string.feature_home_more_eyebrow),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = stringResource(R.string.feature_home_more_title),
            modifier = Modifier.semantics { heading() },
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = stringResource(R.string.feature_home_more_intro),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun MoreTileCard(
    tile: MoreTile,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = TILE_MIN_HEIGHT),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TemplateSpacing.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(TemplateSpacing.extraSmall),
        ) {
            Text(
                text = tile.emoji,
                modifier = Modifier.clearAndSetSemantics {},
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = stringResource(tile.labelRes),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun SecretaryCard(onContactsClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(
            modifier = Modifier.padding(TemplateSpacing.medium),
            verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
        ) {
            Text(
                text = stringResource(R.string.feature_home_more_secretary_title),
                modifier = Modifier.semantics { heading() },
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = stringResource(R.string.feature_home_more_secretary_hours_label),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = stringResource(R.string.feature_home_more_secretary_hours),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = stringResource(R.string.feature_home_more_secretary_phone_label),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = stringResource(R.string.feature_home_more_secretary_phone),
                style = MaterialTheme.typography.titleMedium,
            )
            Button(onClick = onContactsClick, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.feature_home_more_contacts_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoreScreenPreview() {
    ParoquiaTheme { MoreScreen(onDestinationClick = {}, onLinkClick = {}) }
}

private val TILE_MIN_WIDTH = 104.dp
private val TILE_MIN_HEIGHT = 88.dp
private val MAX_CONTENT_WIDTH = 720.dp
