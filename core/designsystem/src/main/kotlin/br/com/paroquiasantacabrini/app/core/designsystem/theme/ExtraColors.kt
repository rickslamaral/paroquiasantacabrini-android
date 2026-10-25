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

package br.com.paroquiasantacabrini.app.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/** Tints from the design prototype that Material 3 has no role for. */
data class ParoquiaExtraColors(
    val cream: Color,
    val blue: Color,
    val rose: Color,
    val sand: Color,
    val calloutText: Color,
    val header: Color,
    val onHeaderMuted: Color,
)

internal val LightExtraColors = ParoquiaExtraColors(
    cream = Color(0xFFF6F0E3),
    blue = Color(0xFFDCE9EF),
    rose = Color(0xFFF1E0E0),
    sand = Color(0xFFF1E4D9),
    calloutText = Color(0xFF625A4C),
    header = Color(0xFF385B48),
    onHeaderMuted = Color(0xFFDCE9DF),
)

internal val DarkExtraColors = ParoquiaExtraColors(
    cream = Color(0xFF3A3A31),
    blue = Color(0xFF263B46),
    rose = Color(0xFF47343A),
    sand = Color(0xFF473C31),
    calloutText = Color(0xFFD9D2C3),
    header = Color(0xFF22372B),
    onHeaderMuted = Color(0xFFC9D8CD),
)

// Theme tokens, provided once by ParoquiaTheme like the local values of MaterialTheme itself.
@Suppress("CompositionLocalAllowlist")
internal val LocalParoquiaExtraColors = staticCompositionLocalOf { LightExtraColors }

/** Extra tints for the current theme, e.g. `MaterialTheme.extraColors.blue`. */
val MaterialTheme.extraColors: ParoquiaExtraColors
    @Composable
    @ReadOnlyComposable
    get() = LocalParoquiaExtraColors.current
