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

package br.com.paroquiasantacabrini.app.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/** Corner radii taken from the prototype: cards 19, quick tiles 17, buttons 15, icon tiles 12. */
val LabShapes = Shapes(
    extraSmall = RoundedCornerShape(size = 12.dp),
    small = RoundedCornerShape(size = 15.dp),
    medium = RoundedCornerShape(size = 17.dp),
    large = RoundedCornerShape(size = 19.dp),
    extraLarge = RoundedCornerShape(size = 24.dp),
)
