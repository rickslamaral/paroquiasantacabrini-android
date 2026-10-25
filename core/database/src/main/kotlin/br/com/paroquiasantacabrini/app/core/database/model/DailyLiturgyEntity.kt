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

/**
 * Daily liturgy readings stored for offline reading.
 *
 * [dateIso] is an ISO-8601 local date (`yyyy-MM-dd`), which sorts chronologically as text.
 */
@Entity(tableName = "daily_liturgy")
public data class DailyLiturgyEntity(
    @PrimaryKey public val dateIso: String,
    public val firstReading: String,
    public val psalm: String,
    public val secondReading: String? = null,
    public val gospel: String,
    public val content: String,
    public val updatedAtEpochMillis: Long = 0L,
)
