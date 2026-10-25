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

package br.com.paroquiasantacabrini.app.feature.liturgy.ui.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MassModeStateTest {

    @Test
    internal fun `starts at the first part with the default text size`() {
        val state = MassModeState()

        assertThat(state.partIndex).isEqualTo(0)
        assertThat(state.textSizeSp).isEqualTo(MassModeState.DEFAULT_TEXT_SIZE_SP)
        assertThat(state.canGoBack).isFalse()
        assertThat(state.canGoForward).isTrue()
    }

    @Test
    internal fun `navigation stays within the four parts`() {
        val last = MassModeState().select(99)
        val first = MassModeState().select(-5)

        assertThat(last.partIndex).isEqualTo(MassParts.lastIndex)
        assertThat(last.next().partIndex).isEqualTo(MassParts.lastIndex)
        assertThat(last.canGoForward).isFalse()
        assertThat(first.partIndex).isEqualTo(0)
        assertThat(first.previous().partIndex).isEqualTo(0)
    }

    @Test
    internal fun `there are four parts`() {
        assertThat(MassParts).hasSize(4)
    }

    @Test
    internal fun `text size is clamped to the supported range`() {
        var state = MassModeState()
        repeat(20) { state = state.larger() }
        assertThat(state.textSizeSp).isEqualTo(MassModeState.MAX_TEXT_SIZE_SP)
        assertThat(state.canGrowText).isFalse()

        repeat(20) { state = state.smaller() }
        assertThat(state.textSizeSp).isEqualTo(MassModeState.MIN_TEXT_SIZE_SP)
        assertThat(state.canShrinkText).isFalse()
    }

    @Test
    internal fun `changing text size keeps the current part`() {
        val state = MassModeState(partIndex = 2).larger()

        assertThat(state.partIndex).isEqualTo(2)
    }
}
