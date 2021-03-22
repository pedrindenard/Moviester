package com.app.moviester.repository

import com.app.moviester.database.dao.MovieDAO
import com.app.moviester.model.Movie
import com.app.moviester.retrofit.Api
import com.app.moviester.retrofit.AppRetrofit
import com.app.moviester.retrofit.response.MovieResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class MovieRepository(private val dao: MovieDAO, private val api: Api = AppRetrofit().movieService) {

    // Busca filmes Popular na API
    suspend fun getPopularMovies(): Response<MovieResponse> {
        return api.getPopularMovies()
    }

    // Busca filmes TopRate na API
    suspend fun getTopRateMovies(): Response<MovieResponse> {
        return api.getTopRateMovies()
    }

    // Busca detalhes dos filmes
    suspend fun getMovieDetails(id: Int): Response<Movie> {
        return api.getMovieDetails(id)
    }

    // Busca filmes na lista
    val findMovieDB: Flow<List<Movie>> = dao.findMovie()

    // Deleta filmes na lista
    suspend fun deleteMovieDB(movie: Movie) {
        dao.deleteMovie(movie)
    }

    // Adiciona filme na lista
    suspend fun saveMovieDB(movie: Movie) {
        movie.id = 1
        dao.saveMovie(movie)
    }
}