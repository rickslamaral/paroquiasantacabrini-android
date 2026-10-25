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

import br.com.paroquiasantacabrini.app.library.pix.PixBrCode

/** Receiver data used to build the donation Pix. Replaced by stored parish data later. */
internal object PixDonationInfo {
    internal const val RECEIVER_NAME: String = "PAROQUIA SANTA FRANCISCA"
    internal const val RECEIVER_CITY: String = "SAO PAULO"
    internal const val INSTITUTION: String = "Itaú Unibanco S.A."

    /** Pix key as registered: the parish CNPJ, digits only. */
    internal const val PIX_KEY: String = "60909843008200"
    internal const val PIX_KEY_FORMATTED: String = "60.909.843/0082-00"
}

internal data class PixUiState(
    internal val receiverName: String,
    internal val institution: String,
    internal val pixKey: String,
    internal val pixKeyFormatted: String,
    internal val payload: String,
    internal val qrCode: QrCodeMatrix,
) {
    internal companion object {
        internal fun forDonation(): PixUiState {
            val payload = PixBrCode.staticPayload(
                pixKey = PixDonationInfo.PIX_KEY,
                merchantName = PixDonationInfo.RECEIVER_NAME,
                merchantCity = PixDonationInfo.RECEIVER_CITY,
            )
            return PixUiState(
                receiverName = PixDonationInfo.RECEIVER_NAME,
                institution = PixDonationInfo.INSTITUTION,
                pixKey = PixDonationInfo.PIX_KEY,
                pixKeyFormatted = PixDonationInfo.PIX_KEY_FORMATTED,
                payload = payload,
                qrCode = QrCodeEncoder.encode(payload),
            )
        }
    }
}

internal enum class PixCopyTarget {
    Key,
    Payload,
}
