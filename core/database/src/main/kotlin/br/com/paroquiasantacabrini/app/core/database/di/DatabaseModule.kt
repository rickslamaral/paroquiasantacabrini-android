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

package br.com.paroquiasantacabrini.app.core.database.di

import android.content.Context
import androidx.room.Room
import br.com.paroquiasantacabrini.app.core.database.ParoquiaDatabase
import br.com.paroquiasantacabrini.app.core.database.dao.DailyLiturgyDao
import br.com.paroquiasantacabrini.app.core.database.dao.ParishDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME: String = "paroquia.db"

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    internal fun provideParoquiaDatabase(
        @ApplicationContext context: Context,
    ): ParoquiaDatabase =
        Room.databaseBuilder(context, ParoquiaDatabase::class.java, DATABASE_NAME).build()

    @Provides
    internal fun provideParishDao(database: ParoquiaDatabase): ParishDao =
        database.parishDao()

    @Provides
    internal fun provideDailyLiturgyDao(database: ParoquiaDatabase): DailyLiturgyDao =
        database.dailyLiturgyDao()
}
