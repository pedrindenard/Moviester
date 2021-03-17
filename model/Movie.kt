package com.app.moviester.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Busca as informações dos filmes disponiveis na API TMDB

@Entity(tableName = "Movie")

data class  Movie(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String?,
    @SerializedName("vote_average") var rate: Float,
    @SerializedName("overview") var description: String?,
    @SerializedName("poster_path") var poster: String?,
    @SerializedName("backdrop_path") var backdrop: String?,
    @SerializedName("release_date") var releaseDate: String?,
    @SerializedName("budget") var budget: String?,
    @SerializedName("popularity") var popularity: Float?,
    @SerializedName("revenue") var revenue: String?,
    @SerializedName("status") var status: String?,
    @SerializedName("vote_count") var vote_count: Int?,
    @SerializedName("runtime") var runtime: Int?,
    @SerializedName("original_language") var language: String?
) : Serializable