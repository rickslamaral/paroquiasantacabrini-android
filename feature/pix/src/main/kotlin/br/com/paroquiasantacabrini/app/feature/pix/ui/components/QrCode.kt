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

package br.com.paroquiasantacabrini.app.feature.pix.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.feature.pix.ui.model.QrCodeMatrix

/**
 * Draws [matrix] as dark modules on a white square. The colors are fixed rather than themed
 * because scanners need dark-on-light contrast in both light and dark mode.
 */
@Composable
internal fun QrCode(
    matrix: QrCodeMatrix,
    description: String,
    modifier: Modifier = Modifier,
    size: Dp = QR_CODE_SIZE,
) {
    Canvas(
        modifier = modifier
            .size(size)
            .aspectRatio(1f)
            .background(Color.White)
            .padding(QR_QUIET_ZONE)
            .semantics { contentDescription = description },
    ) {
        val module = this.size.width / matrix.size
        val moduleSize = Size(module, module)
        for (y in 0 until matrix.size) {
            for (x in 0 until matrix.size) {
                if (matrix[x, y]) {
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(x * module, y * module),
                        size = moduleSize,
                    )
                }
            }
        }
    }
}

private val QR_CODE_SIZE = 264.dp
private val QR_QUIET_ZONE = 16.dp
