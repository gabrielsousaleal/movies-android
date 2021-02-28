package com.example.movies.Services

import com.example.movies.Commons.Models.Movie
import com.example.movies.Commons.Models.MovieList
import com.example.movies.Services.Retrofit.RetrofitInitializer
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
                                 onResult: (error: Boolean, response: MovieList?, errorMessage: String?) -> Unit) {
       val call = RetrofitInitializer().service().getAllMovies(type = type, movieName = name, page = page, api_key = API_KEY, language = LANGUAGE)
       call.enqueue(object: Callback<MovieList> {
           override fun onResponse(call: Call<MovieList>?,
                                   response: Response<MovieList>?) {
               onResult(false, response?.body(), null)
           }

           override fun onFailure(call: Call<MovieList>?,
                                  t: Throwable?) {
               onResult(true, null, t?.message)
           }
   })
   }

    override fun getDetaliedMovie(type: String,
                                  movieID: Int,
                                  onResult: (error: Boolean, response: Movie?, errorMessage: String?) -> Unit) {
        val call = RetrofitInitializer().service().getDetaliedMovie(type = type, apiKey = API_KEY, movieID = movieID, language = LANGUAGE)
        call.enqueue(object: Callback<Movie> {
            override fun onResponse(call: Call<Movie>?,
                                    response: Response<Movie>?) {
                onResult(false, response?.body(), null)
            }

            override fun onFailure(call: Call<Movie>?,
                                   t: Throwable?) {
                onResult(true, null, t?.message)
            }
        })
    }
}