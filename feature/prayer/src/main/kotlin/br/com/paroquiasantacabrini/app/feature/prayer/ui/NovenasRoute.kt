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

package br.com.paroquiasantacabrini.app.feature.prayer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.paroquiasantacabrini.app.feature.prayer.R
import br.com.paroquiasantacabrini.app.feature.prayer.ui.data.PrayerStore
import br.com.paroquiasantacabrini.app.feature.prayer.ui.data.rememberPrayerStore
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.NovenaProgress

@Composable
public fun NovenasRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NovenasRoute(store = rememberPrayerStore(), onBackClick = onBackClick, modifier = modifier)
}

@Composable
internal fun NovenasRoute(
    store: PrayerStore,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // The day is stored on the device so the novena continues after leaving the screen.
    var day by remember(store) { mutableIntStateOf(store.novenaDay) }
    var feedbackRes by remember { mutableStateOf<Int?>(null) }

    NovenasScreen(
        day = day,
        feedbackRes = feedbackRes,
        onContinueClick = {
            val next = NovenaProgress.advance(day)
            feedbackRes = if (next == NovenaProgress.FIRST_DAY) {
                R.string.feature_prayer_novena_restarted
            } else {
                R.string.feature_prayer_novena_progress_saved
            }
            store.novenaDay = next
            day = next
        },
        onBackClick = onBackClick,
        modifier = modifier,
    )
}
