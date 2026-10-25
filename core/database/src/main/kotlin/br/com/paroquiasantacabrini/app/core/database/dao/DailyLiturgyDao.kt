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

package br.com.paroquiasantacabrini.app.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.paroquiasantacabrini.app.core.database.model.DailyLiturgyEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface DailyLiturgyDao {
    @Query("SELECT * FROM daily_liturgy WHERE dateIso = :dateIso")
    public fun observeByDate(dateIso: String): Flow<DailyLiturgyEntity?>

    @Query("SELECT * FROM daily_liturgy WHERE dateIso = :dateIso")
    public suspend fun findByDate(dateIso: String): DailyLiturgyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun upsert(liturgy: DailyLiturgyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun upsertAll(liturgies: List<DailyLiturgyEntity>)

    /** Drops cached readings older than [dateIso] (exclusive) to bound local storage. */
    @Query("DELETE FROM daily_liturgy WHERE dateIso < :dateIso")
    public suspend fun deleteOlderThan(dateIso: String)

    @Query("DELETE FROM daily_liturgy")
    public suspend fun clear()
}
