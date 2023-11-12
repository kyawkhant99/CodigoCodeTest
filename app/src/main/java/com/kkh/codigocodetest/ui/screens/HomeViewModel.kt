package com.kkh.codigocodetest.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.domain.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: MovieRepo) : ViewModel() {

    val upcomingMovies = repo.getAllUpComingMovies()
    val popularMovies = repo.getAllPopularMovies()

    private val _isSuccessUpdate: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isSuccessUpdate = _isSuccessUpdate.asStateFlow()

    init {
        viewModelScope.launch {
            repo.refreshPopularMovies()
            repo.refreshUpcomingMovies()
        }
    }

    fun saveMovie(id: Int) {
        viewModelScope.launch {
            repo.saveMovie(id).onSuccess { res ->

                _isSuccessUpdate.update { res }
            }.onFailure {
                _isSuccessUpdate.update { null }
            }
        }
    }

    fun unSaveMovie(id: Int) {
        viewModelScope.launch {
            repo.unsaveMovie(id).onSuccess { res ->
                _isSuccessUpdate.update { !res }
            }.onFailure {
                _isSuccessUpdate.update { null }
            }
        }
    }
}