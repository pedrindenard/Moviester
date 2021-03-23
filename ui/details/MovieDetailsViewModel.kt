package com.app.moviester.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.moviester.model.Movie
import com.app.moviester.repository.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.Response

// ViewModel

class MovieDetailsViewModel(private val repository: MovieRepository) : ViewModel() {

    val mResponse: MutableLiveData<Response<Movie>> = MutableLiveData()

    // Obtem os detalhes dos filmes
    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            val response = repository.getMovieDetails(id)
            mResponse.value = response
        }
    }

    // Salva o filme e suas informações na lista
    fun saveMovieList(movie: Movie) {
        viewModelScope.launch {
            repository.saveMovieList(movie)
        }
    }
}