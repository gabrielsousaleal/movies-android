package com.example.movies.Services

import com.example.movies.Commons.Models.Movie
import com.example.movies.Commons.Models.MovieList

interface ServicesInterface {
    fun getMoviesByName(type: String, name: String, page: Int, onResult: (error: Boolean, response: MovieList?, errorMessage: String?) -> Unit)
    fun getDetaliedMovie(type: String, movieID: Int, onResult: (error: Boolean, response: Movie?, errorMessage: String?) -> Unit)
}