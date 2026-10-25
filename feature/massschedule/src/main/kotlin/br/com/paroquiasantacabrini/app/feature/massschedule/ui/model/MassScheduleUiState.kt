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

package br.com.paroquiasantacabrini.app.feature.massschedule.ui.model

import androidx.annotation.StringRes
import br.com.paroquiasantacabrini.app.feature.massschedule.R
import java.net.URLEncoder

/** A day label with the times celebrated on it, e.g. `08h`, `10h30`. */
internal data class CelebrationUi(
    @get:StringRes internal val labelRes: Int,
    internal val times: List<String>,
)

/** Parish office hours for a day label, as an opening and closing time. */
internal data class OfficeHoursUi(
    @get:StringRes internal val labelRes: Int,
    internal val opens: String,
    internal val closes: String,
)

internal data class ParishContact(
    internal val address: String,
    internal val postalCode: String,
    internal val phone: String,
    internal val phoneDigits: String,
    internal val email: String,
    internal val pastor: String,
    internal val secretary: String,
) {
    internal val phoneUri: String get() = "tel:+$phoneDigits"
    internal val emailUri: String get() = "mailto:$email"
    internal val mapUri: String
        get() = "https://www.google.com/maps/search/?api=1&query=" +
            URLEncoder.encode("$address, $postalCode", Charsets.UTF_8.name())
}

internal data class MassScheduleUiState(
    internal val celebrations: List<CelebrationUi>,
    internal val adorationTimes: List<String>,
    internal val officeHours: List<OfficeHoursUi>,
    internal val contact: ParishContact,
) {
    internal companion object {
        /** Official parish data. Replaced by stored parish data once the data layer is wired. */
        internal fun forParish(): MassScheduleUiState = MassScheduleUiState(
            celebrations = listOf(
                CelebrationUi(R.string.feature_massschedule_day_tuesday_friday, listOf("19h")),
                CelebrationUi(R.string.feature_massschedule_day_saturday, listOf("18h")),
                CelebrationUi(R.string.feature_massschedule_day_sunday, listOf("08h", "10h30", "18h")),
            ),
            adorationTimes = listOf("18h"),
            officeHours = listOf(
                OfficeHoursUi(R.string.feature_massschedule_day_tuesday_friday, "14h", "18h"),
                OfficeHoursUi(R.string.feature_massschedule_day_saturday, "08h", "12h"),
            ),
            contact = ParishContact(
                address = "Rua Eurídice de Caccine, 286 – Vila Natal, São Paulo - SP",
                postalCode = "CEP 04844-630",
                phone = "(11) 5928-9576",
                phoneDigits = "551159289576",
                email = "santafranciscaxaviercabrini@santoamaro.org.br",
                pastor = "Pe. Vinícius Henrique Maciel",
                secretary = "Mariana",
            ),
        )
    }
}
