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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.component.FixedGrid
import br.com.paroquiasantacabrini.app.core.designsystem.component.InfoRow
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.QuickTile
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.component.SocialLinksRow
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateBackground
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateButton
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.feature.home.R
import br.com.paroquiasantacabrini.app.feature.home.ui.model.MoreDestination

private data class MoreTile(
    internal val destination: MoreDestination,
    internal val glyph: String,
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

private const val TILE_COLUMNS = 4

@Composable
internal fun MoreScreen(
    onDestinationClick: (MoreDestination) -> Unit,
    onLinkClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TemplateBackground(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = MAX_CONTENT_WIDTH)
                    .fillMaxWidth()
                    .padding(horizontal = 17.dp, vertical = 22.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                MoreHeader()
                FixedGrid(items = MoreTiles, columns = TILE_COLUMNS) { index, tile ->
                    QuickTile(
                        glyph = tile.glyph,
                        label = stringResource(tile.labelRes),
                        position = index + 1,
                        onClick = { onDestinationClick(tile.destination) },
                    )
                }
                SectionHeading(
                    text = stringResource(R.string.feature_home_more_secretary_title),
                    modifier = Modifier.padding(top = 10.dp),
                )
                SecretaryCard(onContactsClick = { onDestinationClick(MoreDestination.Contact) })
                SocialLinksRow(onLinkClick = onLinkClick)
            }
        }
    }
}

@Composable
private fun MoreHeader() {
    Column {
        Text(
            text = stringResource(R.string.feature_home_more_eyebrow).uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
        )
        Text(
            text = stringResource(R.string.feature_home_more_title),
            modifier = Modifier.semantics { heading() },
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            text = stringResource(R.string.feature_home_more_intro),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun SecretaryCard(onContactsClick: () -> Unit) {
    ParoquiaCard(contentPadding = PaddingValues(16.dp)) {
        Column {
            InfoRow(
                glyph = "◷",
                title = stringResource(R.string.feature_home_more_secretary_hours_label),
                text = stringResource(R.string.feature_home_more_secretary_hours),
            )
            InfoRow(
                glyph = "☎",
                title = stringResource(R.string.feature_home_more_secretary_phone),
                text = stringResource(R.string.feature_home_more_secretary_phone_label),
            )
            TemplateButton(
                onClick = onContactsClick,
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            ) {
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

private val MAX_CONTENT_WIDTH = 560.dp
