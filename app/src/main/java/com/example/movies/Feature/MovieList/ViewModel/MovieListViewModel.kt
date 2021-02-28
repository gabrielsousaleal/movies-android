package com.example.movies.Feature.MovieList.ViewModel

import com.example.movies.Commons.Models.Movie
import com.example.movies.Services.Services
import com.example.movies.Services.ServicesInterface

enum class MOVIE_TYPE(val string: String) {
    MOVIES("movie"),
    SERIES("tv")
}

class MovieListViewModel(private val service: ServicesInterface = Services()) {

    // MARK: - Private properties

    private var currentPage = 1
    private var currentMovieType = MOVIE_TYPE.MOVIES
    private var currentMovieName: String = ""
    private var movieList: ArrayList<Movie> = arrayListOf<Movie>()

    // MARK: - Private Methods

    private fun updateMovieList(newMovieList: ArrayList<Movie>) {
        if (currentPage == 1) movieList = newMovieList else movieList.addAll(newMovieList)
    }

    // MARK: - Public Methods

    fun setSearchType(type: MOVIE_TYPE, onResult: (response: ArrayList<Movie>?, errorMessage: String?) -> Unit ) {
        currentMovieType = type
        resetConfigs()
        getMoviesByName(movieName = currentMovieName) {response, errorMessage ->
            if (response != null) {
                onResult(response, null)
            }
        }
    }

    fun getMoviesByName(movieName: String, page: Int = 1, onResult: (response: ArrayList<Movie>?, errorMessage: String?) -> Unit) {
        service.getMoviesByName(name = movieName, page = page, type = currentMovieType.string) {response, errorMessage ->
            if (response != null) {
                currentPage = page
                currentMovieName = movieName
                updateMovieList(newMovieList = response.movies)
                onResult(movieList, null)
            }

            if (errorMessage != null) {
                onResult(null, errorMessage)
            }
        }
    }

    fun getDetaliedMovie(movieID: Int, onResult: (response: Movie?, errorMessage: String?) -> Unit) {
        service.getDetaliedMovie(type = currentMovieType.string, movieID = movieID) { response, errorMessage ->
            if (response != null) {
                onResult(response, null)
            }
        }
    }

    fun loadNextPage(onResult: (response: ArrayList<Movie>?, errorMessage: String?) -> Unit) {
        currentPage += 1

        getMoviesByName(movieName = currentMovieName, page = currentPage) { response, errorMessage ->
            if (response != null) {
                onResult(response, null)
            }
        }
    }

    // MARK: - Private Methods

    private fun resetConfigs() {
        currentPage = 1
        movieList.removeAll(movieList)
    }
}