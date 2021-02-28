package com.example.movies.Commons.Models

import com.google.gson.annotations.SerializedName

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
)

data class MovieList (
    @SerializedName("results")
    var movies: ArrayList<Movie>
)