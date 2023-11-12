package com.kkh.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kkh.data.db.dao.MovieDao
import com.kkh.data.db.entities.MovieItemEntity

@Database(entities = [MovieItemEntity::class], version = 1)
abstract class MovieDB : RoomDatabase() {
    abstract fun MovieDao(): MovieDao
}