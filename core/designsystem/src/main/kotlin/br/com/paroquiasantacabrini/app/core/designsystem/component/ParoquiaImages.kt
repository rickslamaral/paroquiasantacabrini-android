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

package br.com.paroquiasantacabrini.app.core.designsystem.component

import androidx.annotation.DrawableRes
import br.com.paroquiasantacabrini.app.core.designsystem.R

/** Images shared across features. The illustrations are demonstration art; the facade is a photograph. */
object ParoquiaImages {
    @DrawableRes
    val MassBanner: Int = R.drawable.core_designsystem_img_missa_banner

    @DrawableRes
    val RosaryBanner: Int = R.drawable.core_designsystem_img_rosario_banner

    @DrawableRes
    val CabriniMission: Int = R.drawable.core_designsystem_img_cabrini_mission

    @DrawableRes
    val SantaCabrini: Int = R.drawable.core_designsystem_img_santa_cabrini

    /** Photograph of the parish church front, used by the home hero and the contact page. */
    @DrawableRes
    val Facade: Int = R.drawable.core_designsystem_img_facade
}
