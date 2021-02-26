package com.example.movies.Services

import com.example.movies.Commons.Models.MovieList

interface ServicesInterface {
    fun getMoviesByName(name: String, page: Int, onResult: (error: Boolean, response: MovieList?, errorMessage: String?) -> Unit)
}