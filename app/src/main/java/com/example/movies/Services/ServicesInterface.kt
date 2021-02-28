package com.example.movies.Services

import com.example.movies.Commons.Models.MovieList

interface ServicesInterface {
    fun getMoviesByName(type: String, name: String, page: Int, onResult: (error: Boolean, response: MovieList?, errorMessage: String?) -> Unit)
}