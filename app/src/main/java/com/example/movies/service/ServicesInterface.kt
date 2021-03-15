package com.example.movies.service

import com.example.movies.commons.model.Movie
import com.example.movies.commons.model.MovieList

interface ServicesInterface {
    fun getMoviesByName(type: String, name: String, page: Int, onResult: (response: MovieList?, errorMessage: String?) -> Unit)
    fun getDetaliedMovie(type: String, movieID: Int, onResult: (response: Movie?, errorMessage: String?) -> Unit)
}