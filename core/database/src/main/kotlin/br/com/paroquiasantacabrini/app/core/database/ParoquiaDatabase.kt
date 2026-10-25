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

package br.com.paroquiasantacabrini.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.paroquiasantacabrini.app.core.database.dao.DailyLiturgyDao
import br.com.paroquiasantacabrini.app.core.database.dao.ParishDao
import br.com.paroquiasantacabrini.app.core.database.model.DailyLiturgyEntity
import br.com.paroquiasantacabrini.app.core.database.model.ParishEntity

@Database(
    entities = [ParishEntity::class, DailyLiturgyEntity::class],
    version = 1,
    exportSchema = true,
)
public abstract class ParoquiaDatabase : RoomDatabase() {
    public abstract fun parishDao(): ParishDao

    public abstract fun dailyLiturgyDao(): DailyLiturgyDao
}
