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

package br.com.paroquiasantacabrini.app.feature.saint

import androidx.compose.runtime.Composable
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.testing.screenshot.ScreenVariantPreviews
import br.com.paroquiasantacabrini.app.feature.saint.ui.SaintCabriniRoute
import br.com.paroquiasantacabrini.app.feature.saint.ui.SaintsRoute
import com.android.tools.screenshot.PreviewTest

@PreviewTest
@ScreenVariantPreviews
@Composable
public fun SaintCabriniVariantPreview() {
    ParoquiaTheme { SaintCabriniRoute(onVisitCommunityClick = {}, onBackClick = {}) }
}

@PreviewTest
@ScreenVariantPreviews
@Composable
public fun SaintsVariantPreview() {
    ParoquiaTheme { SaintsRoute(onSeeCabriniClick = {}, onBackClick = {}) }
}
