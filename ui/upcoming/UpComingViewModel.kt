package com.app.moviester.ui.upcoming

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.moviester.repository.MovieRepository
import com.app.moviester.retrofit.response.MovieResponse
import kotlinx.coroutines.launch
import retrofit2.Response

// ViewModel

class UpComingViewModel(private val repository: MovieRepository) : ViewModel() {

    val mResponse: MutableLiveData<Response<MovieResponse>> = MutableLiveData()

    // Obtem as informações da lista de filmes Upcoming
    fun getUpComingMovie() {
        viewModelScope.launch {
            val response = repository.getUpComingMovies()
            mResponse.value = response
        }
    }
}