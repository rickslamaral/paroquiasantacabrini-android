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

package br.com.paroquiasantacabrini.app.feature.liturgy.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.paroquiasantacabrini.app.feature.liturgy.ui.model.MassModeState
import br.com.paroquiasantacabrini.app.feature.liturgy.ui.model.larger
import br.com.paroquiasantacabrini.app.feature.liturgy.ui.model.next
import br.com.paroquiasantacabrini.app.feature.liturgy.ui.model.previous
import br.com.paroquiasantacabrini.app.feature.liturgy.ui.model.select
import br.com.paroquiasantacabrini.app.feature.liturgy.ui.model.smaller

@Composable
public fun MassModeRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Saved so the part and the text size survive rotation and process recreation.
    var partIndex by rememberSaveable { mutableIntStateOf(0) }
    var textSizeSp by rememberSaveable { mutableIntStateOf(MassModeState.DEFAULT_TEXT_SIZE_SP) }
    val state = MassModeState(partIndex = partIndex, textSizeSp = textSizeSp)

    fun update(new: MassModeState) {
        partIndex = new.partIndex
        textSizeSp = new.textSizeSp
    }

    MassModeScreen(
        state = state,
        onPartClick = { update(state.select(it)) },
        onPreviousClick = { update(state.previous()) },
        onNextClick = { update(state.next()) },
        onSmallerTextClick = { update(state.smaller()) },
        onLargerTextClick = { update(state.larger()) },
        onBackClick = onBackClick,
        modifier = modifier,
    )
}
