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

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.DayOfWeek

internal class MassScheduleUiStateTest {

    private val state = MassScheduleUiState.forParish()
    private val contact = state.contact

    @Test
    internal fun `phone uri is an international tel link`() {
        assertThat(contact.phoneUri).isEqualTo("tel:+551159289576")
    }

    @Test
    internal fun `whatsapp uri uses the same number`() {
        assertThat(contact.whatsappUri).isEqualTo("https://wa.me/551159289576")
    }

    @Test
    internal fun `map uri encodes the street address`() {
        assertThat(contact.mapUri).startsWith("https://www.google.com/maps/search/?api=1&query=")
        assertThat(contact.mapUri).contains("Rua+Eur%C3%ADdice+de+Caccine%2C+286")
        assertThat(contact.mapUri).doesNotContain(" ")
    }

    @Test
    internal fun `sunday has three masses in chronological order`() {
        val sunday = state.celebrations.last()

        assertThat(sunday.masses.map { it.time }).containsExactly("08h", "10h30", "18h")
    }

    @Test
    internal fun `tuesday to friday lists four evening masses`() {
        assertThat(state.celebrations.first().masses.map { it.time })
            .containsExactly("19h", "19h", "19h", "19h")
    }

    @Test
    internal fun `the day tab follows the day of the week`() {
        assertThat(state.initialCelebrationIndex(DayOfWeek.SUNDAY)).isEqualTo(2)
        assertThat(state.initialCelebrationIndex(DayOfWeek.SATURDAY)).isEqualTo(1)
        assertThat(state.initialCelebrationIndex(DayOfWeek.WEDNESDAY)).isEqualTo(0)
        assertThat(state.initialCelebrationIndex(DayOfWeek.MONDAY)).isEqualTo(0)
    }
}
