package com.app.moviester.repository

import com.app.moviester.model.Movie
import com.app.moviester.retrofit.Api
import com.app.moviester.retrofit.AppRetrofit
import com.app.moviester.retrofit.response.MovieResponse
import retrofit2.Response

class MovieRepository(private val api: Api = AppRetrofit().movieService) {

    // Busca filmes Popular na API
    suspend fun getPopularMovies(): Response<MovieResponse> {
        return api.getPopularMovies()
    }

    // Busca filmes TopRate na API
    suspend fun getTopRateMovies(): Response<MovieResponse> {
        return api.getTopRateMovies()
    }

    // Busca filmes UpComing na API
    suspend fun getUpComingMovies(): Response<MovieResponse> {
        return api.getUpComingMovies()
    }

    // Busca detalhes dos filmes
    suspend fun getMovieDetails(id: Int)
            : Response<Movie> {
        return api.getMovieDetails(id)
    }
}