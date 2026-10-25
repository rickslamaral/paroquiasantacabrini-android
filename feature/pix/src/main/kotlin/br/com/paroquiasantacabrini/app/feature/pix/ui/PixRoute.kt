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

package br.com.paroquiasantacabrini.app.feature.pix.ui

import android.content.ClipData
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import br.com.paroquiasantacabrini.app.feature.pix.ui.model.PixCopyTarget
import br.com.paroquiasantacabrini.app.feature.pix.ui.model.PixUiState
import kotlinx.coroutines.launch

@Composable
public fun PixRoute(
    modifier: Modifier = Modifier,
) {
    val state = remember { PixUiState.forDonation() }
    var copiedTarget by remember { mutableStateOf<PixCopyTarget?>(null) }
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    fun copy(target: PixCopyTarget, text: String) {
        scope.launch {
            clipboard.setClipEntry(ClipEntry(ClipData.newPlainText(CLIP_LABEL, text)))
            copiedTarget = target
        }
    }

    PixScreen(
        state = state,
        copiedTarget = copiedTarget,
        onCopyKeyClick = { copy(PixCopyTarget.Key, state.pixKey) },
        onCopyPayloadClick = { copy(PixCopyTarget.Payload, state.payload) },
        modifier = modifier,
    )
}

private const val CLIP_LABEL = "Pix"
