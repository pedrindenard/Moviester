package com.app.moviester.internet.retrofit

import com.app.moviester.internet.model.Movie
import com.app.moviester.internet.retrofit.response.MovieResponse
import com.app.moviester.util.Constants.ApiEndPoint.GET_MOVIE_DETAIL
import com.app.moviester.util.Constants.ApiEndPoint.GET_POPULAR_MOVIE
import com.app.moviester.util.Constants.ApiEndPoint.GET_SEARCH_MOVIE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Endpoints da API The Movie Database

interface ApiService {

    @GET(GET_POPULAR_MOVIE)
    suspend fun getPopularMovies(): Response<MovieResponse>

    @GET(GET_MOVIE_DETAIL)
    suspend fun getMovieDetails(@Path("id") id: Int): Response<Movie>

    @GET(GET_SEARCH_MOVIE)
    suspend fun getSearchMovie(@Query("query") keyword: String): Response<MovieResponse>
}