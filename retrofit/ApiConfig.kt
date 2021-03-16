package com.app.moviester.retrofit

object ApiConfig {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "5179442a4d6ed6eb656ad05e611bad78"
}

object ApiEndPoint {
    const val GET_POPULAR_MOVIE = "movie/popular"
    const val GET_TOP_RATE_MOVIE = "movie/top_rated"
    const val GET_UPCOMING_MOVIE = "discover/movie?sort_by=primary_release_date.asc&primary_release_year=2021"
}