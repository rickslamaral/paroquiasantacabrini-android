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

package br.com.paroquiasantacabrini.app.feature.community.ui.model

import androidx.annotation.StringRes
import br.com.paroquiasantacabrini.app.feature.community.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

/** A regular parish time. [time] is a literal such as `08h`; [timeRes] replaces it with a phrase. */
internal data class CalendarEvent(
    @get:StringRes internal val titleRes: Int,
    internal val time: String? = null,
    @get:StringRes internal val timeRes: Int? = null,
)

/** Regular weekly times confirmed by the parish. Special events are not modelled yet. */
internal object ParishCalendar {
    private const val DAYS_IN_WEEK = 7

    internal fun eventsFor(date: LocalDate): List<CalendarEvent> = when (date.dayOfWeek) {
        DayOfWeek.SUNDAY -> listOf(
            mass("08h"),
            mass("10h30"),
            mass("18h"),
            CalendarEvent(R.string.feature_community_event_confessions, timeRes = R.string.feature_community_event_after_masses),
        )
        DayOfWeek.MONDAY -> emptyList()
        DayOfWeek.SATURDAY -> listOf(secretariat("08h–12h"), mass("18h"))
        DayOfWeek.THURSDAY -> listOf(
            CalendarEvent(R.string.feature_community_event_adoration, time = "18h"),
            secretariat("14h–18h"),
            mass("19h"),
        )
        else -> listOf(secretariat("14h–18h"), mass("19h"))
    }

    internal fun hasEvents(date: LocalDate): Boolean = eventsFor(date).isNotEmpty()

    /** Cells of a Sunday-first month grid: leading `null`s for the blank days, then every date. */
    internal fun monthCells(month: YearMonth): List<LocalDate?> {
        val leadingBlanks = month.atDay(1).dayOfWeek.value % DAYS_IN_WEEK
        return List(leadingBlanks) { null } + (1..month.lengthOfMonth()).map { month.atDay(it) }
    }

    private fun mass(time: String) = CalendarEvent(R.string.feature_community_event_mass, time = time)

    private fun secretariat(time: String) = CalendarEvent(R.string.feature_community_event_secretariat, time = time)
}

internal object ParishContacts {
    internal const val WHATSAPP_URL: String = "https://wa.me/551159289576"
}

internal fun YearMonth.toKey(): Int = year * MONTHS_IN_YEAR + (monthValue - 1)

internal fun monthFromKey(key: Int): YearMonth = YearMonth.of(key / MONTHS_IN_YEAR, key % MONTHS_IN_YEAR + 1)

private const val MONTHS_IN_YEAR = 12
