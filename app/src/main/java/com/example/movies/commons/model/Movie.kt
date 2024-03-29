package com.example.movies.commons.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie (
    @SerializedName("id")
    var id: Int = -1,

    @SerializedName("poster_path")
    var posterPath: String? = null,

    @SerializedName("original_title")
    var originalTitle: String? = null,

    @SerializedName("title")
    var movieName: String? = null,

    @SerializedName("name")
    var nameSerie: String? = null,

    @SerializedName("overview")
    var overView: String? = null
): Serializable

data class MovieList (
    @SerializedName("results")
    var movies: ArrayList<Movie>
)