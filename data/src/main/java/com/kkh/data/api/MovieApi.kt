package com.kkh.data.api

import com.kkh.data.response.movie.MovieDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query(value = "language") language: String="en-US",
        @Query(value = "page") page: Int=1
    ): Response<MovieDataResponse>

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query(value = "language") language: String="en-US",
        @Query(value = "page") page: Int=1
    ): Response<MovieDataResponse>
}