package com.kkh.data.datasource.remote

import com.kkh.data.api.MovieApi
import com.kkh.data.response.movie.MovieDataResponse
import com.kkh.data.util.GenericResponse
import javax.inject.Inject

class MovieRemoteDatasourceImpl @Inject constructor(private val api: MovieApi): MovieRemoteDatasource() {
    override suspend fun getPopularMovies(): GenericResponse<MovieDataResponse> {
        return safeApiCall { api.getPopularMovies() }
    }

    override suspend fun getUpcomingMovies(): GenericResponse<MovieDataResponse> {
        return safeApiCall { api.getUpcomingMovies() }
    }
}