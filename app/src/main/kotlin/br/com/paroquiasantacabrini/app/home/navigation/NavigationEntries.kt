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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import br.com.paroquiasantacabrini.app.feature.home.ui.HomeRoute
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeDestination
import br.com.paroquiasantacabrini.app.feature.liturgy.ui.DailyLiturgyRoute
import br.com.paroquiasantacabrini.app.feature.liturgy.ui.MassModeRoute
import br.com.paroquiasantacabrini.app.feature.massschedule.ui.MassScheduleRoute
import br.com.paroquiasantacabrini.app.feature.pix.ui.PixRoute
import br.com.paroquiasantacabrini.app.feature.prayer.ui.ExamRoute
import br.com.paroquiasantacabrini.app.feature.prayer.ui.NovenasRoute
import br.com.paroquiasantacabrini.app.feature.prayer.ui.PrayerHubRoute
import br.com.paroquiasantacabrini.app.feature.prayer.ui.PrayerRequestRoute
import br.com.paroquiasantacabrini.app.feature.prayer.ui.RosaryRoute
import br.com.paroquiasantacabrini.app.feature.saint.ui.SaintCabriniRoute
import br.com.paroquiasantacabrini.app.feature.saint.ui.SaintsRoute
import br.com.paroquiasantacabrini.app.home.ContentGreen

internal fun EntryProviderScope<NavKey>.homeEntries(backStack: NavBackStack<NavKey>) {
    entry<Screen.ScreenA> {
        HomeRoute(
            onQuickAccessClick = { destination ->
                backStack.add(
                    when (destination) {
                        HomeDestination.TithesAndOfferings -> Screen.Pix
                        HomeDestination.MassSchedule -> Screen.MassSchedule
                        HomeDestination.DailyLiturgy -> Screen.DailyLiturgy
                        HomeDestination.PrayerRequests -> Screen.PrayerRequest
                    },
                )
            },
            // Choosing another parish needs a parish catalog, so it opens a placeholder for now.
            onSwitchParishClick = { backStack.add(Screen.ScreenC) },
            modifier = Modifier.fillMaxSize(),
        )
    }
}

internal fun EntryProviderScope<NavKey>.liturgyEntries(backStack: NavBackStack<NavKey>) {
    entry<Screen.MassMode> {
        MassModeRoute(
            onBackClick = { backStack.removeLastOrNull() },
            modifier = Modifier.fillMaxSize(),
        )
    }
    entry<Screen.DailyLiturgy> {
        DailyLiturgyRoute(
            onPrayerRequestClick = { backStack.add(Screen.PrayerRequest) },
            onBackClick = { backStack.removeLastOrNull() },
            modifier = Modifier.fillMaxSize(),
        )
    }
}

internal fun EntryProviderScope<NavKey>.prayerEntries(backStack: NavBackStack<NavKey>) {
    entry<Screen.PrayerHub> {
        PrayerHubRoute(
            onRosaryClick = { backStack.add(Screen.Rosary) },
            onNovenasClick = { backStack.add(Screen.Novenas) },
            onPrayerRequestClick = { backStack.add(Screen.PrayerRequest) },
            onDailyLiturgyClick = { backStack.add(Screen.DailyLiturgy) },
            onExamClick = { backStack.add(Screen.Exam) },
            modifier = Modifier.fillMaxSize(),
        )
    }
    entry<Screen.Rosary> {
        RosaryRoute(onBackClick = { backStack.removeLastOrNull() }, modifier = Modifier.fillMaxSize())
    }
    entry<Screen.Novenas> {
        NovenasRoute(onBackClick = { backStack.removeLastOrNull() }, modifier = Modifier.fillMaxSize())
    }
    entry<Screen.Exam> {
        ExamRoute(onBackClick = { backStack.removeLastOrNull() }, modifier = Modifier.fillMaxSize())
    }
    entry<Screen.PrayerRequest> {
        PrayerRequestRoute(onBackClick = { backStack.removeLastOrNull() }, modifier = Modifier.fillMaxSize())
    }
}

internal fun EntryProviderScope<NavKey>.parishEntries(backStack: NavBackStack<NavKey>) {
    entry<Screen.Pix> {
        PixRoute(modifier = Modifier.fillMaxSize())
    }
    entry<Screen.MassSchedule> {
        MassScheduleRoute(modifier = Modifier.fillMaxSize())
    }
    entry<Screen.SaintCabrini> {
        SaintCabriniRoute(
            onVisitCommunityClick = { backStack.add(Screen.MassSchedule) },
            onBackClick = { backStack.removeLastOrNull() },
            modifier = Modifier.fillMaxSize(),
        )
    }
    entry<Screen.Saints> {
        SaintsRoute(
            onSeeCabriniClick = { backStack.add(Screen.SaintCabrini) },
            onBackClick = { backStack.removeLastOrNull() },
            modifier = Modifier.fillMaxSize(),
        )
    }
}

internal fun EntryProviderScope<NavKey>.demoEntries(postsContent: @Composable (Modifier) -> Unit) {
    entry<Screen.ScreenB> {
        postsContent(Modifier.fillMaxSize())
    }
    entry<Screen.ScreenC>(metadata = verticalSlideTransitionSpec()) {
        ContentGreen("This is Screen C")
    }
}
