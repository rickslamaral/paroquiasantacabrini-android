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

package br.com.paroquiasantacabrini.app.home.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import br.com.paroquiasantacabrini.app.R
import br.com.paroquiasantacabrini.app.feature.home.ui.model.MoreDestination

/** The four top-level sections shown in the bottom bar. */
internal enum class MainTab(
    internal val root: NavKey,
    internal val glyph: String,
    @get:StringRes internal val labelRes: Int,
) {
    Home(Screen.ScreenA, "⌂", R.string.main_tab_home),
    Calendar(Screen.Calendar, "▦", R.string.main_tab_calendar),
    Pray(Screen.PrayerHub, "♡", R.string.main_tab_pray),
    More(Screen.More, "⋯", R.string.main_tab_more),
}

/** The tab a screen belongs to, so the bar highlights where the reader is. */
internal fun NavKey?.toTab(): MainTab = when (this) {
    Screen.Calendar, Screen.MassSchedule, Screen.MassMode -> MainTab.Calendar
    Screen.PrayerHub, Screen.Rosary, Screen.Novenas, Screen.Exam, Screen.PrayerRequest, Screen.DailyLiturgy ->
        MainTab.Pray
    Screen.More, Screen.Pix, Screen.SaintCabrini, Screen.Saints, Screen.Community, Screen.Groups,
    Screen.Catechesis, Screen.Confirmation, Screen.Notices,
    -> MainTab.More
    else -> MainTab.Home
}

/** Resets the back stack to the root of [tab], keeping Home underneath so Back returns to it. */
internal fun NavBackStack<NavKey>.openTab(tab: MainTab) {
    clear()
    add(Screen.ScreenA)
    if (tab.root != Screen.ScreenA) add(tab.root)
}

internal fun MoreDestination.toScreen(): NavKey = when (this) {
    MoreDestination.MassTimes, MoreDestination.Contact -> Screen.MassSchedule
    MoreDestination.MassMode -> Screen.MassMode
    MoreDestination.DailyLiturgy -> Screen.DailyLiturgy
    MoreDestination.Saints -> Screen.Saints
    MoreDestination.Community -> Screen.Community
    MoreDestination.Calendar -> Screen.Calendar
    MoreDestination.PrayerHub -> Screen.PrayerHub
    MoreDestination.Tithes -> Screen.Pix
    MoreDestination.Groups -> Screen.Groups
    MoreDestination.Catechesis -> Screen.Catechesis
    MoreDestination.Confirmation -> Screen.Confirmation
    MoreDestination.Novenas -> Screen.Novenas
    MoreDestination.Rosary -> Screen.Rosary
    MoreDestination.Exam -> Screen.Exam
}

@Composable
internal fun MainNavigationBar(
    selected: MainTab,
    onTabClick: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier.fillMaxWidth()) {
        MainTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = tab == selected,
                onClick = { onTabClick(tab) },
                icon = { Text(text = tab.glyph, modifier = Modifier.clearAndSetSemantics {}) },
                label = { Text(stringResource(tab.labelRes)) },
            )
        }
    }
}
