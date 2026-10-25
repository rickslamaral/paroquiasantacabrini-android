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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.home.R
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeDestination
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeUiState

@Composable
internal fun HomeContent(
    state: HomeUiState,
    onQuickAccessClick: (HomeDestination) -> Unit,
    onSwitchParishClick: () -> Unit,
    onLinkClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Adaptive columns collapse to a single column on narrow screens or with large font scales.
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = QUICK_ACCESS_MIN_WIDTH),
        modifier = modifier,
        contentPadding = PaddingValues(TemplateSpacing.medium),
        horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.medium),
        verticalArrangement = Arrangement.spacedBy(TemplateSpacing.medium),
    ) {
        item(key = "header", span = { GridItemSpan(maxLineSpan) }) {
            ParishHeader(
                parishName = state.parishName ?: stringResource(R.string.feature_home_default_parish_name),
                onSwitchParishClick = onSwitchParishClick,
            )
        }
        item(key = "mass_card", span = { GridItemSpan(maxLineSpan) }) {
            MassCard(onClick = { onQuickAccessClick(HomeDestination.MassSchedule) })
        }
        item(key = "mass_mode_banner", span = { GridItemSpan(maxLineSpan) }) {
            MassModeBanner(onClick = { onQuickAccessClick(HomeDestination.MassMode) })
        }
        item(key = "quick_access_title", span = { GridItemSpan(maxLineSpan) }) {
            SectionTitleWithAction(
                text = stringResource(R.string.feature_home_quick_access_title),
                actionLabel = stringResource(R.string.feature_home_see_all),
                onActionClick = { onQuickAccessClick(HomeDestination.ShowAll) },
            )
        }
        items(items = QuickAccessItems, key = { it.destination }) { item ->
            QuickAccessCard(
                item = item,
                onClick = { onQuickAccessClick(item.destination) },
            )
        }
        item(key = "announcements_title", span = { GridItemSpan(maxLineSpan) }) {
            SectionTitleWithAction(
                text = stringResource(R.string.feature_home_announcements_title),
                actionLabel = stringResource(R.string.feature_home_all),
                onActionClick = { onQuickAccessClick(HomeDestination.Notices) },
            )
        }
        if (state.announcements.isEmpty()) {
            item(key = "announcements_empty", span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = stringResource(R.string.feature_home_announcements_empty),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        } else {
            items(
                items = state.announcements,
                key = { it.id },
                span = { GridItemSpan(maxLineSpan) },
            ) { announcement ->
                AnnouncementCard(announcement = announcement)
            }
        }
        item(key = "patroness_title", span = { GridItemSpan(maxLineSpan) }) {
            SectionTitleWithAction(
                text = stringResource(R.string.feature_home_patroness_title),
                actionLabel = stringResource(R.string.feature_home_patroness_more),
                onActionClick = { onQuickAccessClick(HomeDestination.SaintCabrini) },
            )
        }
        item(key = "patroness", span = { GridItemSpan(maxLineSpan) }) {
            PatronessCard(onClick = { onQuickAccessClick(HomeDestination.SaintCabrini) })
        }
        item(key = "gallery_title", span = { GridItemSpan(maxLineSpan) }) {
            SectionTitle(text = stringResource(R.string.feature_home_gallery_title))
        }
        item(key = "gallery", span = { GridItemSpan(maxLineSpan) }) {
            ChurchGallery()
        }
        item(key = "links_title", span = { GridItemSpan(maxLineSpan) }) {
            SectionTitle(text = stringResource(R.string.feature_home_links_title))
        }
        items(
            items = ParishLinks,
            key = { it.url },
            span = { GridItemSpan(maxLineSpan) },
        ) { link ->
            ParishLinkCard(
                link = link,
                onClick = { onLinkClick(link.url) },
            )
        }
    }
}

@Composable
private fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier.semantics { heading() },
        style = MaterialTheme.typography.titleLarge,
    )
}

private val QUICK_ACCESS_MIN_WIDTH = 160.dp
