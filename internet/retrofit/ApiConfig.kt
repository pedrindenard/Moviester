package com.app.moviester.internet.retrofit

object ApiConfig {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "5179442a4d6ed6eb656ad05e611bad78"
}

object ApiEndPoint {
    const val GET_POPULAR_MOVIE = "movie/popular"
    const val GET_TOP_RATE_MOVIE = "movie/top_rated"
    const val GET_MOVIE_DETAIL = "movie/{id}"
    const val GET_SEARCH_MOVIE = "search/multi"
}