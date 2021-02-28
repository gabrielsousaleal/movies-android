package com.example.movies.MovieList.ViewModel

import com.example.movies.Commons.Models.Movie
import com.example.movies.Services.Services
import com.example.movies.Services.ServicesInterface

class MovieViewModel(val movie: Movie, val service: ServicesInterface = Services()) {

    // MARK:- Public Properties

    fun getName(): String? {
        if (movie.movieName == null) {
            return movie.nameSerie
        }
        return movie.movieName
    }
}