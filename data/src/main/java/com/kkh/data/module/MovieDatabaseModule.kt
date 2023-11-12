package com.kkh.data.module

import android.content.Context
import androidx.room.Room
import com.kkh.data.api.MovieApi
import com.kkh.data.db.MovieDB
import com.kkh.data.db.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MovieDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MovieDB {
        return Room.databaseBuilder(
            appContext,
            MovieDB::class.java,
            "MovieDB"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideArticleDao(appDatabase: MovieDB): MovieDao {
        return appDatabase.MovieDao()
    }
}