package com.app.moviester.ui.viewmodel

import androidx.lifecycle.*
import com.app.moviester.internet.model.Movie
import com.app.moviester.internet.repository.MovieRepository
import com.app.moviester.internet.retrofit.response.MovieResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val mResponse: MutableLiveData<Response<Movie>> = MutableLiveData()
    val mSearchResponse: MutableLiveData<Response<MovieResponse>> = MutableLiveData()

    // Busca a lista no Movie Database
    val movieListLiveData: LiveData<List<Movie>> = repository.movieListDatabase.asLiveData()

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

    // Delete o filme e suas informações na lista
    fun deleteMovieList(movie: Movie){
        viewModelScope.launch {
            repository.deleteMovieList(movie)
        }
    }

    // Obtem as informações da lista de filmes Popular
    fun getPopularMovie() {
        viewModelScope.launch {
            val response = repository.getPopularMovies()
            mSearchResponse.value = response
        }
    }

    fun getSearchMovie(keyword: String) {
        viewModelScope.launch {
            val response = repository.getSearchMovie(keyword)
            mSearchResponse.value = response
        }
    }

    // Obtem as informações da lista de filmes TopRate
    fun getTopRateMovie() {
        viewModelScope.launch {
            val response = repository.getTopRateMovies()
            mSearchResponse.value = response
        }
    }
}