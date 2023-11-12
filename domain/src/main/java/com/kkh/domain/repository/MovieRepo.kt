package com.kkh.domain.repository

import com.kkh.domain.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface MovieRepo {

    suspend fun refreshUpcomingMovies(): Result<Boolean>

    suspend fun refreshPopularMovies(): Result<Boolean>

    suspend fun saveMovie(id: Int):Result<Boolean>

    suspend fun unsaveMovie(id: Int):Result<Boolean>

    fun getAllPopularMovies(): Flow<List<MovieItem>>

    fun getAllUpComingMovies(): Flow<List<MovieItem>>
}