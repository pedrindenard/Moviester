package com.app.moviester.retrofit

import com.app.moviester.retrofit.ApiEndPoint.GET_POPULAR_MOVIE
import com.app.moviester.retrofit.ApiEndPoint.GET_TOP_RATE_MOVIE
import com.app.moviester.retrofit.ApiEndPoint.GET_UPCOMING_MOVIE
import com.app.moviester.retrofit.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

// Endpoints da API TMDB

interface Api {

    @GET(GET_POPULAR_MOVIE)
    suspend fun getPopularMovies(): Response<MovieResponse>

    @GET(GET_TOP_RATE_MOVIE)
    suspend fun getTopRateMovies(): Response<MovieResponse>

    @GET(GET_UPCOMING_MOVIE)
    suspend fun getUpComingMovies(): Response<MovieResponse>
}