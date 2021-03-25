package com.app.moviester.internet.repository

import com.app.moviester.database.dao.MovieDAO
import com.app.moviester.internet.model.Movie
import com.app.moviester.internet.retrofit.ApiService
import com.app.moviester.internet.retrofit.AppRetrofit
import com.app.moviester.internet.retrofit.response.MovieResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class MovieRepository(private val dao: MovieDAO, private val apiService: ApiService = AppRetrofit().movieService) {

    // Busca filmes Popular na API
    suspend fun getPopularMovies(): Response<MovieResponse> {
        return apiService.getPopularMovies()
    }

    // Busca filmes TopRate na API
    suspend fun getTopRateMovies(): Response<MovieResponse> {
        return apiService.getTopRateMovies()
    }

    // Busca detalhes dos filmes
    suspend fun getMovieDetails(id: Int): Response<Movie> {
        return apiService.getMovieDetails(id)
    }

    // Busca filmes na lista
    val movieListDatabase: Flow<List<Movie>> = dao.findMovieList()

    // Deleta filmes na lista
    suspend fun deleteMovieList(movie: Movie) {
        dao.deleteMovieList(movie)
    }

    // Adiciona filme na lista
    suspend fun saveMovieList(movie: Movie) {
        movie.id
        dao.saveMovieList(movie)
    }

    // Busca os filmes pesquisados
    suspend fun getSearchMovie(keyword: String): Response<MovieResponse> {
        return apiService.getSearchMovie(keyword)
    }
}