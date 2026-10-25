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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.paroquiasantacabrini.app.core.designsystem.component.FixedGrid
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHead
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateBackground
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.feature.home.R
import br.com.paroquiasantacabrini.app.feature.home.ui.components.HomeHero
import br.com.paroquiasantacabrini.app.feature.home.ui.components.MassCard
import br.com.paroquiasantacabrini.app.feature.home.ui.components.MassModeBanner
import br.com.paroquiasantacabrini.app.feature.home.ui.components.NoticeCard
import br.com.paroquiasantacabrini.app.feature.home.ui.components.PatronessCard
import br.com.paroquiasantacabrini.app.feature.home.ui.components.QuickAccessItems
import br.com.paroquiasantacabrini.app.feature.home.ui.components.QuickAccessTile
import br.com.paroquiasantacabrini.app.feature.home.ui.model.AnnouncementUiModel
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeDestination
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeUiState

private const val QUICK_ACCESS_COLUMNS = 4

@Composable
internal fun HomeScreen(
    state: HomeUiState,
    onQuickAccessClick: (HomeDestination) -> Unit,
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
            HomeHero()
            Column(
                modifier = Modifier
                    .widthIn(max = CONTENT_MAX_WIDTH)
                    .fillMaxWidth()
                    .padding(horizontal = 17.dp, vertical = 22.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Welcome()
                MassCard(onClick = { onQuickAccessClick(HomeDestination.MassSchedule) })
                MassModeBanner(onClick = { onQuickAccessClick(HomeDestination.MassMode) })
                SectionHead(
                    text = stringResource(R.string.feature_home_quick_access_title),
                    actionLabel = stringResource(R.string.feature_home_see_all),
                    onActionClick = { onQuickAccessClick(HomeDestination.ShowAll) },
                )
                FixedGrid(items = QuickAccessItems, columns = QUICK_ACCESS_COLUMNS) { index, item ->
                    QuickAccessTile(
                        item = item,
                        position = index + 1,
                        onClick = { onQuickAccessClick(item.destination) },
                    )
                }
                SectionHead(
                    text = stringResource(R.string.feature_home_announcements_title),
                    actionLabel = stringResource(R.string.feature_home_all),
                    onActionClick = { onQuickAccessClick(HomeDestination.Notices) },
                )
                if (state.announcements.isEmpty()) {
                    Text(
                        text = stringResource(R.string.feature_home_announcements_empty),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                state.announcements.forEach { announcement ->
                    NoticeCard(
                        announcement = announcement,
                        onClick = { onQuickAccessClick(HomeDestination.Notices) },
                    )
                }
                SectionHead(
                    text = stringResource(R.string.feature_home_patroness_title),
                    actionLabel = stringResource(R.string.feature_home_patroness_more),
                    onActionClick = { onQuickAccessClick(HomeDestination.SaintCabrini) },
                )
                PatronessCard(onClick = { onQuickAccessClick(HomeDestination.SaintCabrini) })
                Text(
                    text = stringResource(R.string.feature_home_footer_note),
                    modifier = Modifier.fillMaxWidth().padding(top = 14.dp, bottom = 6.dp),
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, letterSpacing = 0.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun Welcome() {
    Column {
        Text(
            text = stringResource(R.string.feature_home_welcome_eyebrow).uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
        )
        Text(
            text = stringResource(R.string.feature_home_welcome_title),
            modifier = Modifier.semantics { heading() },
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            text = stringResource(R.string.feature_home_welcome_text),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    ParoquiaTheme {
        HomeScreen(
            state = HomeUiState(
                announcements = listOf(
                    AnnouncementUiModel(
                        id = "adoration",
                        title = "Adoração ao Santíssimo",
                        message = "Quintas-feiras, às 18h, antes da missa.",
                        dateLabel = "INFORMAÇÃO À COMUNIDADE",
                    ),
                ),
            ),
            onQuickAccessClick = {},
        )
    }
}

private val CONTENT_MAX_WIDTH = 560.dp
