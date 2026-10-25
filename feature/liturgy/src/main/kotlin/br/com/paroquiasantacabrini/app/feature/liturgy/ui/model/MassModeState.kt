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

import androidx.annotation.StringRes
import br.com.paroquiasantacabrini.app.feature.liturgy.R

internal data class MassPart(
    @get:StringRes internal val titleRes: Int,
    @get:StringRes internal val textRes: Int,
)

/** The four parts of the Mass followed in the guided mode, in celebration order. */
internal val MassParts: List<MassPart> = listOf(
    MassPart(R.string.feature_liturgy_mass_part_1_title, R.string.feature_liturgy_mass_part_1_text),
    MassPart(R.string.feature_liturgy_mass_part_2_title, R.string.feature_liturgy_mass_part_2_text),
    MassPart(R.string.feature_liturgy_mass_part_3_title, R.string.feature_liturgy_mass_part_3_text),
    MassPart(R.string.feature_liturgy_mass_part_4_title, R.string.feature_liturgy_mass_part_4_text),
)

/** Position in the celebration and reading text size, with the bounds enforced in one place. */
internal data class MassModeState(
    internal val partIndex: Int = 0,
    internal val textSizeSp: Int = DEFAULT_TEXT_SIZE_SP,
) {
    internal val canGoBack: Boolean get() = partIndex > 0
    internal val canGoForward: Boolean get() = partIndex < MassParts.lastIndex
    internal val canShrinkText: Boolean get() = textSizeSp > MIN_TEXT_SIZE_SP
    internal val canGrowText: Boolean get() = textSizeSp < MAX_TEXT_SIZE_SP

    internal companion object {
        internal const val DEFAULT_TEXT_SIZE_SP: Int = 15
        internal const val MIN_TEXT_SIZE_SP: Int = 14
        internal const val MAX_TEXT_SIZE_SP: Int = 21
        internal const val TEXT_SIZE_STEP_SP: Int = 1
    }
}

internal fun MassModeState.select(index: Int): MassModeState =
    copy(partIndex = index.coerceIn(0, MassParts.lastIndex))

internal fun MassModeState.next(): MassModeState = select(partIndex + 1)

internal fun MassModeState.previous(): MassModeState = select(partIndex - 1)

internal fun MassModeState.smaller(): MassModeState =
    copy(textSizeSp = (textSizeSp - MassModeState.TEXT_SIZE_STEP_SP).coerceAtLeast(MassModeState.MIN_TEXT_SIZE_SP))

internal fun MassModeState.larger(): MassModeState =
    copy(textSizeSp = (textSizeSp + MassModeState.TEXT_SIZE_STEP_SP).coerceAtMost(MassModeState.MAX_TEXT_SIZE_SP))
