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

import java.text.Normalizer

/**
 * Builds static Pix "copia e cola" payloads (EMV® BR Code) as specified by the Banco Central do
 * Brasil, including the CRC16-CCITT checksum.
 */
object PixBrCode {

    private const val MAX_NAME_LENGTH = 25
    private const val MAX_CITY_LENGTH = 15
    private const val CRC_POLYNOMIAL = 0x1021
    private const val CRC_INITIAL = 0xFFFF
    private const val CRC_MASK = 0xFFFF
    private const val CRC_TOP_BIT = 0x8000
    private const val BITS_PER_BYTE = 8
    private const val CRC_HEX_LENGTH = 4
    private const val HEX_RADIX = 16
    private const val BYTE_MASK = 0xFF
    private const val FIRST_PRINTABLE_ASCII = 0x20
    private const val LAST_PRINTABLE_ASCII = 0x7E

    /**
     * Returns the payload for a reusable (static) Pix charge.
     *
     * @param pixKey the key exactly as registered; digits only for a CNPJ or CPF key.
     * @param merchantName receiver name, normalized to uppercase ASCII and cut to 25 characters.
     * @param merchantCity receiver city, normalized to uppercase ASCII and cut to 15 characters.
     */
    fun staticPayload(
        pixKey: String,
        merchantName: String,
        merchantCity: String,
    ): String {
        require(pixKey.isNotBlank()) { "Pix key must not be blank" }
        val accountInfo = field("00", "br.gov.bcb.pix") + field("01", pixKey)
        val withoutCrc = field("00", "01") +
            field("01", "11") +
            field("26", accountInfo) +
            field("52", "0000") +
            field("53", "986") +
            field("58", "BR") +
            field("59", sanitize(merchantName, MAX_NAME_LENGTH)) +
            field("60", sanitize(merchantCity, MAX_CITY_LENGTH)) +
            field("62", field("05", "***")) +
            "6304"
        return withoutCrc + crc16(withoutCrc)
    }

    /** CRC16/CCITT-FALSE as four uppercase hexadecimal digits. */
    internal fun crc16(input: String): String {
        var crc = CRC_INITIAL
        for (byte in input.toByteArray(Charsets.US_ASCII)) {
            crc = crc xor ((byte.toInt() and BYTE_MASK) shl BITS_PER_BYTE)
            repeat(BITS_PER_BYTE) {
                crc = if (crc and CRC_TOP_BIT != 0) {
                    ((crc shl 1) xor CRC_POLYNOMIAL) and CRC_MASK
                } else {
                    (crc shl 1) and CRC_MASK
                }
            }
        }
        return crc.toString(HEX_RADIX).uppercase().padStart(CRC_HEX_LENGTH, '0')
    }

    private fun field(id: String, value: String): String =
        id + value.length.toString().padStart(2, '0') + value

    private fun sanitize(value: String, maxLength: Int): String =
        Normalizer.normalize(value, Normalizer.Form.NFD)
            .filter { it.code in FIRST_PRINTABLE_ASCII..LAST_PRINTABLE_ASCII }
            .uppercase()
            .take(maxLength)
            .trim()
}
