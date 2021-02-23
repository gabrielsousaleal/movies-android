package com.example.movies.Services

import com.example.movies.Commons.Models.MovieList

interface ServicesInterface {
    fun getMoviesByName(name: String, onResult: (error: Boolean, response: MovieList?, errorMessage: String?) -> Unit)
}