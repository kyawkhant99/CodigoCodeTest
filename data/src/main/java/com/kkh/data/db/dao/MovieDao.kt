package com.kkh.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kkh.data.db.entities.MovieItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<MovieItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie:MovieItemEntity)

    @Query("select * from MOVIES")
    fun getAllMovies():Flow<List<MovieItemEntity>>

    @Query("select * from MOVIES where isPopular = 1")
    fun getAllPopularMovies():Flow<List<MovieItemEntity>>

    @Query("select * from MOVIES where isPopular = 0")
    fun getAllUpcomingMovies():Flow<List<MovieItemEntity>>

    @Query("select * from MOVIES WHERE isSaved = 1")
    fun getAllSavedMovies(): Flow<List<MovieItemEntity>>

    @Query("delete from MOVIES where movieID in (:movies) and isPopular = 0")
    suspend fun deleteUpcomingMovies(movies:List<Int>)

    @Query("delete from MOVIES where movieID in (:movies) and isPopular = 1")
    suspend fun deletePopularMovies(movies:List<Int>)

    @Query("Update MOVIES set isSaved = 0 where id=:id")
    suspend fun unsaveMovie(id:Int):Int

    @Query("Update MOVIES set isSaved = 1 where id=:id")
    suspend fun saveMovie(id:Int):Int
}