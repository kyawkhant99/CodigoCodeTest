package com.kkh.data.datasource.local

import com.kkh.data.db.entities.MovieItemEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalDatasource {

    suspend fun insertAllMovies(movies: List<MovieItemEntity>)

    suspend fun insertMovie(movie:MovieItemEntity)

    fun getAllMovies(): Flow<List<MovieItemEntity>>

    fun getAllPopularMovies():Flow<List<MovieItemEntity>>

    fun getAllUpcomingMovies():Flow<List<MovieItemEntity>>

    suspend fun deleteUpcomings(movieIDList:List<Int>)

    suspend fun deletePopulars(movieIDList:List<Int>)

    suspend fun saveMovie(id:Int):Int

    suspend fun unsaveMovie(id:Int):Int
}