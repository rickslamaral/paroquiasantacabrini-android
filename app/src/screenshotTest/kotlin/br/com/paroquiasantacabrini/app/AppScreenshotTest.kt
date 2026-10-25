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

package br.com.paroquiasantacabrini.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.NavigationEventDispatcherOwner
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.home.navigation.MainNavigation
import br.com.paroquiasantacabrini.app.home.navigation.Screen
import com.android.tools.screenshot.PreviewTest

internal const val PHONE_WIDTH = 390
internal const val PHONE_HEIGHT = 844

/** The preview renderer has no Activity, so give NavDisplay the back-event dispatcher it expects. */
@Composable
private fun WithNavigationEvents(content: @Composable () -> Unit) {
    val owner = remember {
        object : NavigationEventDispatcherOwner {
            override val navigationEventDispatcher = NavigationEventDispatcher()
        }
    }
    CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides owner, content = content)
}

/** A full-app capture of [start] at the same 390x844 size as the design prototype. */
@Composable
internal fun AppAt(start: NavKey) {
    WithNavigationEvents {
        ParoquiaTheme {
            MainNavigation(
                backStack = rememberNavBackStack(Screen.ScreenA, start),
                postsContent = {},
            )
        }
    }
}

@PreviewTest
@Preview(name = "home", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppHomePreview() {
    WithNavigationEvents { ParoquiaTheme { MainNavigation(postsContent = {}) } }
}

@PreviewTest
@Preview(name = "mass-times", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppMassTimesPreview() {
    AppAt(Screen.MassSchedule)
}

@PreviewTest
@Preview(name = "mass-mode", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppMassModePreview() {
    AppAt(Screen.MassMode)
}

@PreviewTest
@Preview(name = "pray", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppPrayPreview() {
    AppAt(Screen.PrayerHub)
}

@PreviewTest
@Preview(name = "calendar", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppCalendarPreview() {
    AppAt(Screen.Calendar)
}

@PreviewTest
@Preview(name = "saint", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppSaintPreview() {
    AppAt(Screen.SaintCabrini)
}

@PreviewTest
@Preview(name = "pix", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppPixPreview() {
    AppAt(Screen.Pix)
}

@PreviewTest
@Preview(name = "more", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppMorePreview() {
    AppAt(Screen.More)
}
