package com.kkh.data.datasource.local

import com.kkh.data.db.dao.MovieDao
import com.kkh.data.db.entities.MovieItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDatasourceImpl @Inject constructor(private val dao: MovieDao) :
    MovieLocalDatasource {
    override suspend fun insertAllMovies(movies: List<MovieItemEntity>) {
        dao.insertAllMovies(movies)
    }

    override suspend fun insertMovie(movie: MovieItemEntity) {
        dao.insertMovie(movie)
    }

    override fun getAllMovies(): Flow<List<MovieItemEntity>> {
        return dao.getAllMovies()
    }

    override fun getAllPopularMovies(): Flow<List<MovieItemEntity>> {
        return dao.getAllPopularMovies()
    }

    override fun getAllUpcomingMovies(): Flow<List<MovieItemEntity>> {
        return dao.getAllUpcomingMovies()
    }

    override suspend fun deleteUpcomings(movieIDList: List<Int>) {
        dao.deletePopularMovies(movieIDList)
    }

    override suspend fun deletePopulars(movieIDList: List<Int>) {
        dao.deletePopularMovies(movieIDList)
    }

    override suspend fun saveMovie(id: Int):Int {
        return dao.saveMovie(id)
    }

    override suspend fun unsaveMovie(id: Int):Int {
        return dao.unsaveMovie(id)
    }

}