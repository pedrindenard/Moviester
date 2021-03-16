package com.app.moviester.retrofit.response

import com.app.moviester.model.Movie
import com.google.gson.annotations.SerializedName

// Recebe resposta dos endpoint

data class MovieResponse(
    @SerializedName("page") var page: Int?,
    @SerializedName("total_pages") var totalPage: Int?,
    @SerializedName("results") val results: List<Movie>?,
    @SerializedName("total_results") var totalResult: Int?
)