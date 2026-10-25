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

package br.com.paroquiasantacabrini.app.library.pix

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class PixBrCodeTest {

    @Test
    fun `crc16 matches the CCITT-FALSE check value`() {
        assertThat(PixBrCode.crc16("123456789")).isEqualTo("29B1")
    }

    @Test
    fun `static payload for the parish cnpj key matches the reference`() {
        val payload = PixBrCode.staticPayload(
            pixKey = "60909843008200",
            merchantName = "PAROQUIA SANTA FRANCISCA",
            merchantCity = "SAO PAULO",
        )

        assertThat(payload).isEqualTo(
            "00020101021126360014br.gov.bcb.pix0114609098430082005204000053039865802BR" +
                "5924PAROQUIA SANTA FRANCISCA6009SAO PAULO62070503***6304F322",
        )
    }

    @Test
    fun `payload checksum covers everything before it`() {
        val payload = PixBrCode.staticPayload("60909843008200", "Paróquia Teste", "São Paulo")

        val body = payload.dropLast(4)
        assertThat(body).endsWith("6304")
        assertThat(payload.takeLast(4)).isEqualTo(PixBrCode.crc16(body))
    }

    @Test
    fun `name and city are normalized to ascii uppercase and truncated`() {
        val payload = PixBrCode.staticPayload(
            pixKey = "60909843008200",
            merchantName = "Paróquia Santa Francisca Xavier Cabrini",
            merchantCity = "São Bernardo do Campo",
        )

        assertThat(payload).contains("5924PAROQUIA SANTA FRANCISCA6015")
        assertThat(payload).contains("6015SAO BERNARDO DO62")
    }

    @Test
    fun `blank key is rejected`() {
        assertThrows(IllegalArgumentException::class.java) {
            PixBrCode.staticPayload("  ", "NOME", "CIDADE")
        }
    }
}
