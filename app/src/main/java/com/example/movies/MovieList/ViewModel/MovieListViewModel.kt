package com.example.movies.MovieList.ViewModel

import com.example.movies.Commons.Models.Movie
import com.example.movies.Commons.Models.MovieList
import com.example.movies.Services.Services

class MovieListViewModel {
    private val service = Services()

    fun getMoviesByName(movieName: String, onResult: (error: Boolean, response: List<Movie>?, errorMessage: String?) -> Unit) {
        service.getMoviesByName(name = movieName) { error, response, errorMessage ->

            if (response != null) {
                onResult(false, response.movies, null)
            }

            if (error == true) {
                onResult(true, null, errorMessage)
            }
        }
    }
}