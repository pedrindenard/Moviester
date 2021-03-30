package com.app.moviester.ui.viewmodel

import androidx.lifecycle.*
import com.app.moviester.internet.model.Movie
import com.app.moviester.internet.repository.MovieRepository
import com.app.moviester.internet.model.MovieResponse
import com.app.moviester.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val mResponseDetails: MutableLiveData<Response<Movie>> = MutableLiveData()
    val mResponseMovie: MutableLiveData<Response<MovieResponse>> = MutableLiveData()
    val mResponseSearch: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    var mResponseSearchNew: MovieResponse? = null

    // Busca a lista no Movie Database
    val movieListLiveData: LiveData<List<Movie>> = repository.movieListDatabase.asLiveData()

    // Obtem os detalhes dos filmes
    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            val response = repository.getMovieDetails(id)
            mResponseDetails.value = response
        }
    }

    // Salva o filme e suas informações na lista
    fun saveMovieList(movie: Movie) {
        viewModelScope.launch {
            repository.saveMovieList(movie)
        }
    }

    // Delete o filme e suas informações na lista
    fun deleteMovieList(movie: Movie) {
        viewModelScope.launch {
            repository.deleteMovieList(movie)
        }
    }

    // Obtem as informações da lista de filmes Popular
    fun getPopularMovie() {
        viewModelScope.launch {
            val response = repository.getPopularMovies()
            mResponseMovie.value = response
        }
    }

    // Envia os resultados da pesquisa para o fragment
    fun getSearchMovie(keyword: String) = viewModelScope.launch {
        searchMovie(keyword)
    }

    // Obtem os resultados da pesquisa feita
    private suspend fun searchMovie(searchQuery: String) {
        mResponseSearch.postValue(Resource.Loading())
        val response = repository.getSearchMovie(searchQuery)
        mResponseSearch.postValue(searchMovieResponse(response))
    }


    // Obtem os filmes através da pesquisa
    private fun searchMovieResponse(response: Response<MovieResponse>): Resource<MovieResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (mResponseSearchNew == null) {
                    mResponseSearchNew = resultResponse
                }
                return Resource.Success(mResponseSearchNew ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
