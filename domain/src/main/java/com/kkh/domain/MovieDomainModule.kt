package com.kkh.domain

import com.kkh.domain.repository.MovieRepo
import com.kkh.domain.repository.MovieRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class MovieDomainModule {
    @Binds
    @Singleton
    abstract fun bindArticlesRepository(
        articleRepoImpl: MovieRepoImpl
    ): MovieRepo
}