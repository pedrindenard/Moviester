package com.app.moviester.ui.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.moviester.repository.MovieRepository
import com.app.moviester.retrofit.response.MovieResponse
import kotlinx.coroutines.launch
import retrofit2.Response

// ViewModel

class PopularViewModel(private val repository: MovieRepository) : ViewModel() {

    val mResponse: MutableLiveData<Response<MovieResponse>> = MutableLiveData()

    // Obtem as informações da lista de filmes Popular
    fun getPopularMovie() {
        viewModelScope.launch {
            val response = repository.getPopularMovies()
            mResponse.value = response
        }
    }
}