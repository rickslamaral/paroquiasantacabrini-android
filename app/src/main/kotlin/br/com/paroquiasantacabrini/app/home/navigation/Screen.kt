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

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    public data object ScreenA : NavKey

    @Serializable
    public data object ScreenB : NavKey

    @Serializable
    public data object ScreenC : NavKey

    @Serializable
    public data object Pix : NavKey

    @Serializable
    public data object MassSchedule : NavKey

    @Serializable
    public data object Contact : NavKey

    @Serializable
    public data object MassMode : NavKey

    @Serializable
    public data object DailyLiturgy : NavKey

    @Serializable
    public data object SaintCabrini : NavKey

    @Serializable
    public data object Saints : NavKey

    @Serializable
    public data object PrayerHub : NavKey

    @Serializable
    public data object Rosary : NavKey

    @Serializable
    public data object Novenas : NavKey

    @Serializable
    public data object Exam : NavKey

    @Serializable
    public data object PrayerRequest : NavKey

    @Serializable
    public data object Community : NavKey

    @Serializable
    public data object Groups : NavKey

    @Serializable
    public data object Catechesis : NavKey

    @Serializable
    public data object Confirmation : NavKey

    @Serializable
    public data object Calendar : NavKey

    @Serializable
    public data object Notices : NavKey

    @Serializable
    public data object More : NavKey
}
