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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import br.com.paroquiasantacabrini.app.feature.home.R
import br.com.paroquiasantacabrini.app.feature.home.ui.model.AnnouncementUiModel
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeDestination
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeUiState

@Composable
public fun HomeRoute(
    onQuickAccessClick: (HomeDestination) -> Unit,
    onSwitchParishClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // The parish and its notices will come from the offline-first data layer once a repository
    // exists. Until then the dashboard shows the default parish and the regular Adoration notice.
    val adoration = AnnouncementUiModel(
        id = "adoration",
        title = stringResource(R.string.feature_home_notice_adoration_title),
        message = stringResource(R.string.feature_home_notice_adoration_text),
        dateLabel = stringResource(R.string.feature_home_notice_label),
    )
    val state = remember(adoration) { HomeUiState(announcements = listOf(adoration)) }
    val uriHandler = LocalUriHandler.current

    HomeScreen(
        state = state,
        onQuickAccessClick = onQuickAccessClick,
        onSwitchParishClick = onSwitchParishClick,
        // A device without a browser cannot open the link; ignore it.
        onLinkClick = { url -> runCatching { uriHandler.openUri(url) } },
        modifier = modifier,
    )
}
