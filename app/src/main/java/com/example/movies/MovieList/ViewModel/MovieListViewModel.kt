package com.example.movies.MovieList.ViewModel

import com.example.movies.Commons.Models.Movie
import com.example.movies.Commons.Models.MovieList
import com.example.movies.Services.Services
import com.example.movies.Services.ServicesInterface

class MovieListViewModel(private val service: ServicesInterface = Services()) {

    // MARK: - Private properties

    private var currentPage = 1
    private var currentMovieName: String = ""
    private var movieList: ArrayList<Movie> = arrayListOf<Movie>()

    // MARK: - Private Methods

    private fun updateMovieList(newMovieList: ArrayList<Movie>) {
        if (currentPage == 1) movieList = newMovieList else movieList.addAll(newMovieList)
    }

    // MARK: - Public Methods

    fun getMoviesByName(movieName: String, page: Int = 1, onResult: (error: Boolean, response: ArrayList<Movie>?, errorMessage: String?) -> Unit) {
        service.getMoviesByName(name = movieName, page = page) { error, response, errorMessage ->
            if (response != null) {
                currentPage = page
                currentMovieName = movieName
                updateMovieList(newMovieList = response.movies)
                onResult(false, movieList, null)
            }

            if (error) {
                onResult(true, null, errorMessage)
            }
        }
    }

    fun loadNextPage(onResult: (error: Boolean, response: ArrayList<Movie>?, errorMessage: String?) -> Unit) {
        currentPage += 1

        getMoviesByName(movieName = currentMovieName, page = currentPage) { error, response, errorMessage ->
            if (response != null) {
                onResult(false, response, null)
            }
        }
    }
}