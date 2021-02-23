package com.example.movies.Commons.Models

import com.google.gson.annotations.SerializedName

data class Movie (
@SerializedName("id")
var id: Int,

@SerializedName("original_title")
var name: String
)

data class MovieList (
    @SerializedName("results")
    var movies: ArrayList<Movie>
    )