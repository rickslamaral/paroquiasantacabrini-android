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

import br.com.paroquiasantacabrini.app.feature.community.R
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.YearMonth

internal class ParishCalendarTest {

    private val monday = LocalDate.of(2026, 10, 26)
    private val tuesday = LocalDate.of(2026, 10, 27)
    private val thursday = LocalDate.of(2026, 10, 29)
    private val saturday = LocalDate.of(2026, 10, 24)
    private val sunday = LocalDate.of(2026, 10, 25)

    @Test
    internal fun `monday has no regular times`() {
        assertThat(ParishCalendar.eventsFor(monday)).isEmpty()
        assertThat(ParishCalendar.hasEvents(monday)).isFalse()
    }

    @Test
    internal fun `tuesday to friday has the office and the 19h mass`() {
        val events = ParishCalendar.eventsFor(tuesday)

        assertThat(events.map { it.time }).containsExactly("14h–18h", "19h")
    }

    @Test
    internal fun `thursday starts with adoration at 18h`() {
        val events = ParishCalendar.eventsFor(thursday)

        assertThat(events.first().titleRes).isEqualTo(R.string.feature_community_event_adoration)
        assertThat(events.first().time).isEqualTo("18h")
        assertThat(events.map { it.time }).containsExactly("18h", "14h–18h", "19h")
    }

    @Test
    internal fun `saturday has the morning office and the 18h mass`() {
        assertThat(ParishCalendar.eventsFor(saturday).map { it.time }).containsExactly("08h–12h", "18h")
    }

    @Test
    internal fun `sunday has three masses and confessions after the morning masses`() {
        val events = ParishCalendar.eventsFor(sunday)

        assertThat(events.filter { it.titleRes == R.string.feature_community_event_mass }.map { it.time })
            .containsExactly("08h", "10h30", "18h")
        val confessions = events.last()
        assertThat(confessions.titleRes).isEqualTo(R.string.feature_community_event_confessions)
        assertThat(confessions.timeRes).isEqualTo(R.string.feature_community_event_after_masses)
        assertThat(confessions.time).isNull()
    }

    @Test
    internal fun `october 2026 starts on a thursday so the grid has four leading blanks`() {
        val cells = ParishCalendar.monthCells(YearMonth.of(2026, 10))

        assertThat(cells.take(4)).containsOnly(null)
        assertThat(cells[4]).isEqualTo(LocalDate.of(2026, 10, 1))
        assertThat(cells).hasSize(4 + 31)
        assertThat(cells.last()).isEqualTo(LocalDate.of(2026, 10, 31))
    }

    @Test
    internal fun `a month starting on sunday has no leading blanks`() {
        val cells = ParishCalendar.monthCells(YearMonth.of(2026, 2))

        assertThat(cells.first()).isEqualTo(LocalDate.of(2026, 2, 1))
        assertThat(cells).hasSize(28)
    }

    @Test
    internal fun `month key round trips across the year boundary`() {
        listOf(YearMonth.of(2026, 1), YearMonth.of(2026, 12), YearMonth.of(2027, 1)).forEach {
            assertThat(monthFromKey(it.toKey())).isEqualTo(it)
        }
    }
}
