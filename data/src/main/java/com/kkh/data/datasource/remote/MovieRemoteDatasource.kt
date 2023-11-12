package com.kkh.data.datasource.remote

import com.kkh.core.extensions.unknownErrorOnNull
import com.kkh.data.response.error.MovieApiErrorResponse
import com.kkh.data.response.movie.MovieDataResponse
import com.kkh.data.util.GenericResponse
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class MovieRemoteDatasource {

    abstract suspend fun getPopularMovies(): GenericResponse<MovieDataResponse>
    abstract suspend fun getUpcomingMovies(): GenericResponse<MovieDataResponse>

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): GenericResponse<T> {

        return withContext(Dispatchers.IO) {
            try {

                val response: Response<T> = apiCall()
                if (response.isSuccessful) {
                    GenericResponse.Success(data = response.body()!!)
                } else {
                    val errorResponse: MovieApiErrorResponse? =
                        response.errorBody().convertErrorBody()
                    GenericResponse.Error(
                        errorMessage = errorResponse?.message ?: "Something went wrong"
                    )
                }

            } catch (e: HttpException) {
                GenericResponse.Error(errorMessage = e.message.unknownErrorOnNull())
            } catch (e: IOException) {
                GenericResponse.Error("Please check your network connection")
            } catch (e: Exception) {
                GenericResponse.Error(errorMessage = "Something went wrong!")
            }
        }
    }

    private fun ResponseBody?.convertErrorBody(): MovieApiErrorResponse? {
        return try {
            this?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(MovieApiErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }

}





