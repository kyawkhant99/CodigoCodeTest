package com.kkh.data.response.movie

data class MovieDataResponse(
    val page: Int,
    val results: List<MovieItemResponse>,
    val total_pages: Int,
    val total_results: Int
)