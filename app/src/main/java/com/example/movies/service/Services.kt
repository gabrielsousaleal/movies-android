package com.example.movies.service

import com.example.movies.commons.model.Movie
import com.example.movies.commons.model.MovieList
import com.example.movies.service.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Services: ServicesInterface {

    // MARK: - Constants

    private val API_KEY = "dcf373a212e3fd454f97f09a273a42e2"
    private val LANGUAGE = "pt-BR"

    // MARK: - Public Methods

    override fun getMoviesByName(type: String,
                                 name: String,
                                 page: Int,
                                 onResult: (response: MovieList?, errorMessage: String?) -> Unit) {
       val call = RetrofitInitializer().service().getAllMovies(type = type, movieName = name, page = page, api_key = API_KEY, language = LANGUAGE)
       call.enqueue(object: Callback<MovieList> {
           override fun onResponse(call: Call<MovieList>?,
                                   response: Response<MovieList>?) {
               onResult(response?.body(), null)
           }

           override fun onFailure(call: Call<MovieList>?,
                                  t: Throwable?) {
               onResult(null, t?.message)
           }
   })
   }

    override fun getDetaliedMovie(type: String,
                                  movieID: Int,
                                  onResult: (response: Movie?, errorMessage: String?) -> Unit) {
        val call = RetrofitInitializer().service().getDetaliedMovie(type = type, apiKey = API_KEY, movieID = movieID, language = LANGUAGE)
        call.enqueue(object: Callback<Movie> {
            override fun onResponse(call: Call<Movie>?,
                                    response: Response<Movie>?) {
                onResult(response?.body(), null)
            }

            override fun onFailure(call: Call<Movie>?,
                                   t: Throwable?) {
                onResult(null, t?.message)
            }
        })
    }
}