package com.kkh.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "MOVIES"
)
data class MovieItemEntity(
    val backdropPath: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieID:Int,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val isSaved: Boolean,
    val isPopular:Boolean
)