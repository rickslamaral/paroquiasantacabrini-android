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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateBackground
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.feature.home.ui.components.HomeContent
import br.com.paroquiasantacabrini.app.feature.home.ui.model.AnnouncementUiModel
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeDestination
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeUiState

@Composable
internal fun HomeScreen(
    state: HomeUiState,
    onQuickAccessClick: (HomeDestination) -> Unit,
    onSwitchParishClick: () -> Unit,
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
            HomeContent(
                state = state,
                onQuickAccessClick = onQuickAccessClick,
                onSwitchParishClick = onSwitchParishClick,
                onLinkClick = onLinkClick,
                modifier = Modifier
                    .widthIn(max = MAX_CONTENT_WIDTH)
                    .fillMaxHeight(),
            )
        }
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
                        id = "1",
                        title = "Festa da padroeira",
                        message = "Missa solene às 19h seguida de confraternização.",
                        dateLabel = "13 de novembro",
                    ),
                ),
            ),
            onQuickAccessClick = {},
            onSwitchParishClick = {},
            onLinkClick = {},
        )
    }
}

private val MAX_CONTENT_WIDTH = 720.dp
