package com.app.moviester.internet.model

import com.app.moviester.internet.model.Movie
import com.google.gson.annotations.SerializedName

// Recebe resposta dos endpoint

data class MovieResponse(
    @SerializedName("results") val results: List<Movie>
)