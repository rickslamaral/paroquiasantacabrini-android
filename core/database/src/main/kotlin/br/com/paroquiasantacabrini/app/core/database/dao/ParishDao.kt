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
import br.com.paroquiasantacabrini.app.core.database.model.ParishEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface ParishDao {
    @Query("SELECT * FROM parishes ORDER BY name COLLATE NOCASE")
    public fun observeAll(): Flow<List<ParishEntity>>

    @Query("SELECT * FROM parishes WHERE id = :id")
    public fun observeById(id: String): Flow<ParishEntity?>

    @Query("SELECT * FROM parishes WHERE id = :id")
    public suspend fun findById(id: String): ParishEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun upsert(parish: ParishEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun upsertAll(parishes: List<ParishEntity>)

    @Query("DELETE FROM parishes WHERE id = :id")
    public suspend fun deleteById(id: String)

    @Query("DELETE FROM parishes")
    public suspend fun clear()
}
