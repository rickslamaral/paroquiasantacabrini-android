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

package br.com.paroquiasantacabrini.app.feature.community.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.paroquiasantacabrini.app.feature.community.ui.model.monthFromKey
import br.com.paroquiasantacabrini.app.feature.community.ui.model.toKey
import java.time.LocalDate
import java.time.YearMonth

@Composable
public fun CalendarRoute(
    onAllMassTimesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CalendarRoute(today = LocalDate.now(), onAllMassTimesClick = onAllMassTimesClick, modifier = modifier)
}

@Composable
internal fun CalendarRoute(
    today: LocalDate,
    onAllMassTimesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Saved so the shown month and the selected date survive rotation and returning to the screen.
    var monthKey by rememberSaveable { mutableIntStateOf(YearMonth.from(today).toKey()) }
    var selectedEpochDay by rememberSaveable { mutableLongStateOf(today.toEpochDay()) }
    val month = monthFromKey(monthKey)

    fun showMonth(newMonth: YearMonth) {
        monthKey = newMonth.toKey()
        selectedEpochDay = newMonth.atDay(1).toEpochDay()
    }

    CalendarScreen(
        month = month,
        selected = LocalDate.ofEpochDay(selectedEpochDay),
        today = today,
        onDayClick = { selectedEpochDay = it.toEpochDay() },
        onPreviousMonthClick = { showMonth(month.minusMonths(1)) },
        onNextMonthClick = { showMonth(month.plusMonths(1)) },
        onAllMassTimesClick = onAllMassTimesClick,
        modifier = modifier,
    )
}
