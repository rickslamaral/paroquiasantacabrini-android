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

package br.com.paroquiasantacabrini.app.feature.prayer.ui.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PrayerModelTest {

    @Test
    internal fun `novena advances one day at a time`() {
        assertThat(NovenaProgress.advance(1)).isEqualTo(2)
        assertThat(NovenaProgress.advance(8)).isEqualTo(9)
    }

    @Test
    internal fun `novena restarts after the ninth day`() {
        assertThat(NovenaProgress.advance(9)).isEqualTo(NovenaProgress.FIRST_DAY)
    }

    @Test
    internal fun `novena day is clamped to one through nine`() {
        assertThat(NovenaProgress.clamp(-3)).isEqualTo(1)
        assertThat(NovenaProgress.clamp(42)).isEqualTo(9)
    }

    @Test
    internal fun `rosary has six steps and five mysteries`() {
        assertThat(RosarySteps).hasSize(6)
        assertThat(GloriousMysteries).hasSize(5)
        assertThat(RosaryProgress.clamp(99)).isEqualTo(5)
        assertThat(RosaryProgress.clamp(-1)).isEqualTo(0)
    }

    @Test
    internal fun `blank text cannot be saved`() {
        assertThat(PrayerRequestRules.validate("   ", acknowledged = true)).isEqualTo(RequestError.EmptyText)
    }

    @Test
    internal fun `saving requires the acknowledgement`() {
        assertThat(PrayerRequestRules.validate("Pela paz", acknowledged = false))
            .isEqualTo(RequestError.NotAcknowledged)
    }

    @Test
    internal fun `valid request has no error`() {
        assertThat(PrayerRequestRules.validate("Pela paz", acknowledged = true)).isNull()
    }

    @Test
    internal fun `text is limited to the maximum length`() {
        val long = "a".repeat(PrayerRequestRules.MAX_LENGTH + 50)

        assertThat(PrayerRequestRules.limit(long)).hasSize(PrayerRequestRules.MAX_LENGTH)
    }
}
