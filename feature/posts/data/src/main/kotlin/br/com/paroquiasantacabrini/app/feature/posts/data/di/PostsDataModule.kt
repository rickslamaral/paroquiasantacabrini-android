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

package br.com.paroquiasantacabrini.app.feature.posts.data.di

import br.com.paroquiasantacabrini.app.feature.posts.data.local.InMemoryPostsLocalDataSource
import br.com.paroquiasantacabrini.app.feature.posts.data.local.PostsLocalDataSource
import br.com.paroquiasantacabrini.app.feature.posts.data.remote.PostsRemoteDataSource
import br.com.paroquiasantacabrini.app.feature.posts.data.remote.RetrofitPostsRemoteDataSource
import br.com.paroquiasantacabrini.app.feature.posts.data.repository.PostsRepositoryImpl
import br.com.paroquiasantacabrini.app.feature.posts.domain.repository.PostsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PostsDataModule {

    @Binds
    @Singleton
    internal abstract fun bindPostsRepository(
        implementation: PostsRepositoryImpl,
    ): PostsRepository

    @Binds
    @Singleton
    internal abstract fun bindPostsRemoteDataSource(
        implementation: RetrofitPostsRemoteDataSource,
    ): PostsRemoteDataSource

    @Binds
    @Singleton
    internal abstract fun bindPostsLocalDataSource(
        implementation: InMemoryPostsLocalDataSource,
    ): PostsLocalDataSource
}
