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

package br.com.paroquiasantacabrini.app.feature.pix.ui.model

import com.google.zxing.BinaryBitmap
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class QrCodeEncoderTest {

    @Test
    internal fun `matrix is square and starts with a finder pattern`() {
        val matrix = QrCodeEncoder.encode("hello")

        assertThat(matrix.size).isGreaterThanOrEqualTo(21)
        // Top-left finder: dark outer ring, light inner ring, dark 3x3 core.
        assertThat((0..6).all { matrix[it, 0] && matrix[0, it] }).isTrue()
        assertThat(matrix[1, 1]).isFalse()
        assertThat(matrix[3, 3]).isTrue()
    }

    @Test
    internal fun `donation qr code decodes back to the pix payload`() {
        val state = PixUiState.forDonation()

        assertThat(decode(state.qrCode)).isEqualTo(state.payload)
    }

    @Test
    internal fun `donation state targets the parish cnpj key`() {
        val state = PixUiState.forDonation()

        assertThat(state.pixKey).isEqualTo("60909843008200")
        assertThat(state.payload).contains("br.gov.bcb.pix0114" + "60909843008200")
    }

    private fun decode(matrix: QrCodeMatrix): String {
        val scale = 4
        val quiet = 4
        val side = (matrix.size + 2 * quiet) * scale
        val pixels = IntArray(side * side) { index ->
            val x = index % side / scale - quiet
            val y = index / side / scale - quiet
            val dark = x in 0 until matrix.size && y in 0 until matrix.size && matrix[x, y]
            if (dark) BLACK else WHITE
        }
        val bitmap = BinaryBitmap(HybridBinarizer(RGBLuminanceSource(side, side, pixels)))
        return QRCodeReader().decode(bitmap).text
    }

    private companion object {
        private const val BLACK = 0xFF000000.toInt()
        private const val WHITE = 0xFFFFFFFF.toInt()
    }
}
