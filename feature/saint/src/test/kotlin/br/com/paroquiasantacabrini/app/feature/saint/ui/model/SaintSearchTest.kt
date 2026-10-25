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

package br.com.paroquiasantacabrini.app.feature.saint.ui.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SaintSearchTest {

    @Test
    internal fun `blank query returns the whole directory`() {
        assertThat(searchSaints("")).hasSize(SaintDirectory.size)
        assertThat(searchSaints("   ")).hasSize(SaintDirectory.size)
    }

    @Test
    internal fun `search ignores case and accents`() {
        assertThat(searchSaints("antonio").map { it.name }).containsExactly("Santo Antônio de Pádua")
        assertThat(searchSaints("CABRINI").map { it.name }).containsExactly("Santa Francisca Xavier Cabrini")
        assertThat(searchSaints("padua")).hasSize(1)
    }

    @Test
    internal fun `unknown name returns an empty list`() {
        assertThat(searchSaints("zzz")).isEmpty()
    }

    @Test
    internal fun `only the patroness has a full page`() {
        assertThat(SaintDirectory.filter { it.isPatroness }.map { it.name })
            .containsExactly("Santa Francisca Xavier Cabrini")
    }

    @Test
    internal fun `biography has four facts and four timeline entries and three sources`() {
        assertThat(CabriniFacts).hasSize(4)
        assertThat(CabriniTimeline).hasSize(4)
        assertThat(CabriniSources).hasSize(3)
        assertThat(CabriniSources.all { it.url.startsWith("https://") }).isTrue()
    }
}
