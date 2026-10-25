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

package br.com.paroquiasantacabrini.app.feature.massschedule.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.paroquiasantacabrini.app.feature.massschedule.ui.model.MassScheduleUiState
import br.com.paroquiasantacabrini.app.feature.massschedule.ui.model.initialCelebrationIndex
import java.time.LocalDate

@Composable
public fun MassScheduleRoute(
    onBackClick: () -> Unit,
    onDirectionsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = remember { MassScheduleUiState.forParish() }
    val initialDayIndex = remember { state.initialCelebrationIndex(LocalDate.now().dayOfWeek) }

    MassScheduleScreen(
        state = state,
        initialDayIndex = initialDayIndex,
        onDirectionsClick = onDirectionsClick,
        modifier = modifier,
        onBackClick = onBackClick,
    )
}
