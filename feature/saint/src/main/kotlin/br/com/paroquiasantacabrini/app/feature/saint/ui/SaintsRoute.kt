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

package br.com.paroquiasantacabrini.app.feature.saint.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.paroquiasantacabrini.app.feature.saint.ui.model.SaintDirectory

@Composable
public fun SaintsRoute(
    onSeeCabriniClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var query by rememberSaveable { mutableStateOf("") }
    var selectedName by rememberSaveable { mutableStateOf<String?>(null) }
    val selected = SaintDirectory.firstOrNull { it.name == selectedName }

    SaintsScreen(
        query = query,
        selectedSaint = selected,
        onQueryChange = { query = it },
        onSaintClick = { saint ->
            // The patroness has a full page; the other profiles are demonstration cards.
            if (saint.isPatroness) onSeeCabriniClick() else selectedName = saint.name
        },
        onSeeCabriniClick = onSeeCabriniClick,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}
