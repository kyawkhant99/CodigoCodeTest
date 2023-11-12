package com.kkh.domain.model

import com.kkh.data.db.entities.MovieItemEntity
import com.kkh.data.response.movie.MovieItemResponse
import java.io.Serializable

data class MovieItem(
    val backdropPath: String,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val isSaved: Boolean
): Serializable


fun MovieItemResponse.mapToEntity()=MovieItemEntity(
    movieID =id,
    backdropPath = backdrop_path.orEmpty(),
    originalTitle = original_title.orEmpty(),
    overview = overview.orEmpty(),
    posterPath = poster_path.orEmpty(),
    popularity = popularity ?: 0.0,
    releaseDate = release_date.orEmpty(),
    title = title.orEmpty(),
    isSaved = false,
    isPopular = true
)

fun MovieItemEntity.mapToDomain()=MovieItem(
    id=id,
    backdropPath = backdropPath,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    popularity = popularity,
    releaseDate = releaseDate,
    title = title,
    isSaved = isSaved
)

