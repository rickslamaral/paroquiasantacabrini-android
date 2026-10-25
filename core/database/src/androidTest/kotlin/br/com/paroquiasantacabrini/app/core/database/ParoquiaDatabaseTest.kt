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

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.paroquiasantacabrini.app.core.database.model.DailyLiturgyEntity
import br.com.paroquiasantacabrini.app.core.database.model.ParishEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
public class ParoquiaDatabaseTest {

    private lateinit var database: ParoquiaDatabase

    @Before
    public fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            ParoquiaDatabase::class.java,
        ).allowMainThreadQueries().build()
    }

    @After
    public fun tearDown() {
        database.close()
    }

    @Test
    public fun parishUpsertReadAndDelete(): Unit = runTest {
        val parish = ParishEntity(
            id = "santa-cabrini",
            name = "Paróquia Santa Cabrini",
            address = "Rua Exemplo, 100",
            neighborhood = "Centro",
            city = "São Paulo",
            pixKey = "paroquia@example.com",
        )

        database.parishDao().upsert(parish)
        assertThat(database.parishDao().findById(parish.id)).isEqualTo(parish)
        assertThat(database.parishDao().observeAll().first()).containsExactly(parish)

        database.parishDao().deleteById(parish.id)
        assertThat(database.parishDao().findById(parish.id)).isNull()
    }

    @Test
    public fun parishUpsertReplacesExistingRow(): Unit = runTest {
        val original = ParishEntity("p1", "Antiga", "Rua A", "Centro", "Cidade")
        val updated = original.copy(name = "Nova")

        database.parishDao().upsert(original)
        database.parishDao().upsert(updated)

        assertThat(database.parishDao().observeAll().first()).containsExactly(updated)
    }

    @Test
    public fun dailyLiturgyIsReadableByDateAndPrunedByAge(): Unit = runTest {
        val old = liturgy("2026-01-01")
        val today = liturgy("2026-06-15")

        database.dailyLiturgyDao().upsertAll(listOf(old, today))
        assertThat(database.dailyLiturgyDao().observeByDate("2026-06-15").first()).isEqualTo(today)

        database.dailyLiturgyDao().deleteOlderThan("2026-06-01")
        assertThat(database.dailyLiturgyDao().findByDate("2026-01-01")).isNull()
        assertThat(database.dailyLiturgyDao().findByDate("2026-06-15")).isEqualTo(today)
    }

    private fun liturgy(dateIso: String) = DailyLiturgyEntity(
        dateIso = dateIso,
        firstReading = "Primeira leitura",
        psalm = "Salmo",
        gospel = "Evangelho",
        content = "Conteúdo completo",
    )
}
