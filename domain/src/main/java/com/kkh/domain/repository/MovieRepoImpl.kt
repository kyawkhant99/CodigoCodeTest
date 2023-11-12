package com.kkh.domain.repository

import android.util.Log
import com.kkh.core.extensions.unknownErrorOnNull
import com.kkh.data.datasource.local.MovieLocalDatasource
import com.kkh.data.datasource.remote.MovieRemoteDatasource
import com.kkh.data.db.entities.MovieItemEntity
import com.kkh.data.response.movie.MovieItemResponse
import com.kkh.data.util.GenericResponse
import com.kkh.domain.model.MovieItem
import com.kkh.domain.model.mapToDomain
import com.kkh.domain.model.mapToEntity
import com.kkh.domain.util.MovieException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepoImpl @Inject constructor(
    private val remoteDS: MovieRemoteDatasource,
    private val localDS: MovieLocalDatasource
) : MovieRepo {
    override suspend fun refreshUpcomingMovies(): Result<Boolean> {


        return when (val result = remoteDS.getUpcomingMovies()) {
            is GenericResponse.Error -> {
                (Result.failure(MovieException(result.message.unknownErrorOnNull())))
            }

            is GenericResponse.Success -> {

                val localMovies = try {
                    localDS.getAllUpcomingMovies().first()
                } catch (e: NoSuchElementException) {
                    emptyList<MovieItemEntity>()
                }

                val netRes = result.data?.results ?: emptyList()
                localMovies.ifEmpty {
                    val entities = netRes.map(MovieItemResponse::mapToEntity)
                        .map { it.copy(isPopular = false, isSaved = false) }
                    localDS.insertAllMovies(entities)
                }
                if (localMovies.isNotEmpty()) {

                    val staleData = localMovies.map { it.movieID } - netRes.map { it.id }.toSet()
                    localDS.deleteUpcomings(staleData)

                    val newData = netRes.map { it.id } - localMovies.map { it.movieID }.toSet()
                    val newEntitiesData = netRes.filter { newData.contains(it.id) }
                        .map(MovieItemResponse::mapToEntity).map {
                        it.copy(isSaved = false, isPopular = false)
                    }
                    localDS.insertAllMovies(newEntitiesData)
                }
                (Result.success(true))
            }
        }
    }

    override suspend fun refreshPopularMovies(): Result<Boolean> {
        return when (val result = remoteDS.getPopularMovies()) {
            is GenericResponse.Error -> {
                (Result.failure(MovieException(result.message.unknownErrorOnNull())))
            }

            is GenericResponse.Success -> {
                val localMovies = try {
                    localDS.getAllPopularMovies().first()
                } catch (e: NoSuchElementException) {
                    emptyList<MovieItemEntity>()
                }

                val netRes = result.data?.results ?: emptyList()
                localMovies.ifEmpty {
                    val entities = netRes.map(MovieItemResponse::mapToEntity)
                        .map { it.copy(isPopular = true, isSaved = false) }
                    localDS.insertAllMovies(entities)
                }
                if (localMovies.isNotEmpty()) {

                    val staleData = localMovies.map { it.movieID } - netRes.map { it.id }.toSet()
                    localDS.deletePopulars(staleData)

                    val newData = netRes.map { it.id } - localMovies.map { it.movieID }.toSet()
                    val newEntitiesData = netRes.filter { newData.contains(it.id) }
                        .map(MovieItemResponse::mapToEntity).map {
                        it.copy(isSaved = false, isPopular = false)
                    }
                    localDS.insertAllMovies(newEntitiesData)
                }
                (Result.success(true))
            }
        }
    }

    override suspend fun saveMovie(id: Int):Result<Boolean> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val result = localDS.saveMovie(id)
                Result.success(result == 1)
            }catch (e:Exception) {
                Result.failure(e)
            }
        }


    }

    override suspend fun unsaveMovie(id: Int): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val result = localDS.unsaveMovie(id)
                Result.success(result == 1)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override fun getAllPopularMovies(): Flow<List<MovieItem>> {
        return localDS.getAllPopularMovies().map { it.map(MovieItemEntity::mapToDomain) }
    }

    override fun getAllUpComingMovies(): Flow<List<MovieItem>> {
        return localDS.getAllUpcomingMovies().map { it.map(MovieItemEntity::mapToDomain) }
    }


}