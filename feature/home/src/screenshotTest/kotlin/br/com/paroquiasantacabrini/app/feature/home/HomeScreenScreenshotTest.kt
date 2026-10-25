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

package br.com.paroquiasantacabrini.app.feature.home

import androidx.compose.runtime.Composable
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.testing.screenshot.ScreenSizePreviews
import br.com.paroquiasantacabrini.app.core.testing.screenshot.ScreenVariantPreviews
import br.com.paroquiasantacabrini.app.feature.home.ui.HomeScreen
import br.com.paroquiasantacabrini.app.feature.home.ui.MoreRoute
import br.com.paroquiasantacabrini.app.feature.home.ui.model.AnnouncementUiModel
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeUiState
import com.android.tools.screenshot.PreviewTest

@PreviewTest
@ScreenSizePreviews
@Composable
public fun HomeScreenSizePreview() {
    HomeScreenWithAnnouncement()
}

@PreviewTest
@ScreenVariantPreviews
@Composable
public fun HomeScreenVariantPreview() {
    HomeScreenWithAnnouncement()
}

@Composable
private fun HomeScreenWithAnnouncement() {
    ParoquiaTheme {
        HomeScreen(
            state = HomeUiState(
                announcements = listOf(
                    AnnouncementUiModel(
                        id = "1",
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

@PreviewTest
@ScreenVariantPreviews
@Composable
public fun MoreScreenVariantPreview() {
    ParoquiaTheme { MoreRoute(onDestinationClick = {}) }
}
