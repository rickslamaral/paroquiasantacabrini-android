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

package br.com.paroquiasantacabrini.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

/** Page background: the prototype's soft warm tint at the top fading into the paper color. */
@Composable
fun TemplateBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    Surface(
        modifier = modifier,
        color = colors.background,
    ) {
        Box(
            modifier = Modifier.background(
                Brush.verticalGradient(
                    0f to colors.surfaceContainer,
                    BACKGROUND_FADE_END to colors.background,
                ),
            ),
            content = content,
        )
    }
}

private const val BACKGROUND_FADE_END = 0.35f
