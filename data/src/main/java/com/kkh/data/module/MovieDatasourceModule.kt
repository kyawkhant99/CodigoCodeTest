package com.kkh.data.module

import com.kkh.data.datasource.local.MovieLocalDatasource
import com.kkh.data.datasource.local.MovieLocalDatasourceImpl
import com.kkh.data.datasource.remote.MovieRemoteDatasource
import com.kkh.data.datasource.remote.MovieRemoteDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieDatasourceModule {

    @Binds
    @Singleton
    abstract fun bindArticleRemoteDatasource(
        movieRemoteImpl: MovieRemoteDatasourceImpl
    ): MovieRemoteDatasource


    @Binds
    @Singleton
    abstract fun bindArticleLocalDatasource(
        movieLocalImpl: MovieLocalDatasourceImpl
    ): MovieLocalDatasource
}