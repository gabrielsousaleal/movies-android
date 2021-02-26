package com.example.movies.MovieList.ViewModel

import com.example.movies.Commons.Models.Movie
import com.example.movies.Commons.Models.MovieList
import com.example.movies.Services.Services

class MovieListViewModel {

    // MARK: - Private properties

    private val service = Services()
    private var currentPage = 1
    private var currentMovieName: String = ""

    // MARK: - Public Properties

    var movieList: List<Movie> = emptyList()

    // MARK: - Private Methods

    private fun incrementPage(newPage: List<Movie>) {
        currentPage += 1
        movieList += newPage
    }

    // MARK: - Public Methods

    fun getMoviesByName(movieName: String, onResult: (error: Boolean, response: List<Movie>?, errorMessage: String?) -> Unit) {
        service.getMoviesByName(name = movieName, page = currentPage) { error, response, errorMessage ->

            if (response != null) {
                currentMovieName = movieName
                movieList = response.movies
                onResult(false, movieList, null)
            }

            if (error) {
                onResult(true, null, errorMessage)
            }
        }
    }

    fun loadNextPage(onResult: (error: Boolean, response: List<Movie>?, errorMessage: String?) -> Unit) {
        val nextPage = currentPage + 1
        service.getMoviesByName(name = currentMovieName, page = nextPage) { error, response, errorMessage ->

          if (response != null) {
              incrementPage(newPage = response.movies)
              onResult(false, movieList, null)
          }

          if (error) {
              onResult(true, null, errorMessage)
          }
      }
    }
}