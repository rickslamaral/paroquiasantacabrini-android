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
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeDestination
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeUiState

@Composable
public fun HomeRoute(
    onQuickAccessClick: (HomeDestination) -> Unit,
    onSwitchParishClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Parish and announcements will be collected from the offline-first data layer once a
    // repository exists; until then the dashboard shows the default parish and no announcements.
    val state = remember { HomeUiState() }
    val uriHandler = LocalUriHandler.current

    HomeScreen(
        state = state,
        onQuickAccessClick = onQuickAccessClick,
        onSwitchParishClick = onSwitchParishClick,
        onLinkClick = uriHandler::openUri,
        modifier = modifier,
    )
}
