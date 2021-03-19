package com.app.moviester.retrofit.response

import com.app.moviester.model.Movie
import com.google.gson.annotations.SerializedName

// Recebe resposta dos endpoint

data class MovieResponse(
    @SerializedName("results") val results: List<Movie>?
)