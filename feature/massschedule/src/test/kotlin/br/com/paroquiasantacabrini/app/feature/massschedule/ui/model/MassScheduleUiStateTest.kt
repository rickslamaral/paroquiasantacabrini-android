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

internal class MassScheduleUiStateTest {

    private val contact = MassScheduleUiState.forParish().contact

    @Test
    internal fun `phone uri is an international tel link`() {
        assertThat(contact.phoneUri).isEqualTo("tel:+551159289576")
    }

    @Test
    internal fun `map uri encodes the street address and postal code`() {
        assertThat(contact.mapUri).startsWith("https://www.google.com/maps/search/?api=1&query=")
        assertThat(contact.mapUri).contains("Rua+Eur%C3%ADdice+de+Caccine%2C+286")
        assertThat(contact.mapUri).doesNotContain(" ")
    }

    @Test
    internal fun `sunday has three masses in chronological order`() {
        val sunday = MassScheduleUiState.forParish().celebrations.last()

        assertThat(sunday.times).containsExactly("08h", "10h30", "18h")
    }
}
