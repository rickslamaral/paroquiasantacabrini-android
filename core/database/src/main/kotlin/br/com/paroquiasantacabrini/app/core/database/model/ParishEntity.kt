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

package br.com.paroquiasantacabrini.app.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/** A parish (church community) cached locally so the app works offline. */
@Entity(tableName = "parishes")
public data class ParishEntity(
    @PrimaryKey public val id: String,
    public val name: String,
    public val address: String,
    public val neighborhood: String,
    public val city: String,
    public val state: String? = null,
    public val phone: String? = null,
    public val pixKey: String? = null,
    public val pixKeyHolder: String? = null,
    public val updatedAtEpochMillis: Long = 0L,
)
