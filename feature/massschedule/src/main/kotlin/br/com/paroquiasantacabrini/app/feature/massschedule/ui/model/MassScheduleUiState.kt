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
import java.time.DayOfWeek

/** One Mass: the weekday it is celebrated on and its time, e.g. `10h30`. */
internal data class MassUi(
    @get:StringRes internal val weekdayRes: Int,
    internal val time: String,
)

/** A day tab (e.g. "Tuesday to Friday") with the Masses celebrated on it. */
internal data class CelebrationUi(
    @get:StringRes internal val labelRes: Int,
    internal val masses: List<MassUi>,
)

internal data class ParishContact(
    internal val street: String,
    internal val locality: String,
    internal val postalCode: String,
    internal val phone: String,
    internal val phoneDigits: String,
    internal val email: String,
    internal val pastor: String,
    internal val secretary: String,
) {
    internal val phoneUri: String get() = "tel:+$phoneDigits"
    internal val whatsappUri: String get() = "https://wa.me/$phoneDigits"
    internal val emailUri: String get() = "mailto:$email"
    internal val mapUri: String
        get() = "https://www.google.com/maps/search/?api=1&query=" +
            URLEncoder.encode("$street, $locality", Charsets.UTF_8.name())
}

internal data class MassScheduleUiState(
    internal val celebrations: List<CelebrationUi>,
    internal val contact: ParishContact,
) {
    internal companion object {
        /** Official parish data. Replaced by stored parish data once the data layer is wired. */
        internal fun forParish(): MassScheduleUiState = MassScheduleUiState(
            celebrations = listOf(
                CelebrationUi(
                    labelRes = R.string.feature_massschedule_day_tuesday_friday,
                    masses = listOf(
                        MassUi(R.string.feature_massschedule_day_tuesday, "19h"),
                        MassUi(R.string.feature_massschedule_day_wednesday, "19h"),
                        MassUi(R.string.feature_massschedule_day_thursday, "19h"),
                        MassUi(R.string.feature_massschedule_day_friday, "19h"),
                    ),
                ),
                CelebrationUi(
                    labelRes = R.string.feature_massschedule_day_saturday,
                    masses = listOf(MassUi(R.string.feature_massschedule_day_saturday, "18h")),
                ),
                CelebrationUi(
                    labelRes = R.string.feature_massschedule_day_sunday,
                    masses = listOf(
                        MassUi(R.string.feature_massschedule_day_sunday, "08h"),
                        MassUi(R.string.feature_massschedule_day_sunday, "10h30"),
                        MassUi(R.string.feature_massschedule_day_sunday, "18h"),
                    ),
                ),
            ),
            contact = ParishContact(
                street = "Rua Eurídice de Caccine, 286",
                locality = "Vila Natal, São Paulo – SP",
                postalCode = "04844-630",
                phone = "(11) 5928-9576",
                phoneDigits = "551159289576",
                email = "santafranciscaxaviercabrini@santoamaro.org.br",
                pastor = "Pe. Vinícius Henrique Maciel",
                secretary = "Mariana",
            ),
        )
    }
}

/** The day tab to open first: the group that matches [day], or the next one on a Monday. */
internal fun MassScheduleUiState.initialCelebrationIndex(day: DayOfWeek): Int = when (day) {
    DayOfWeek.SATURDAY -> SATURDAY_INDEX
    DayOfWeek.SUNDAY -> SUNDAY_INDEX
    else -> 0
}.coerceIn(0, celebrations.lastIndex)

private const val SATURDAY_INDEX = 1
private const val SUNDAY_INDEX = 2
