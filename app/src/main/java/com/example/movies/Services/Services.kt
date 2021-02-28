package com.example.movies.Services

import com.example.movies.Commons.Models.MovieList
import com.example.movies.Services.Retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Services: ServicesInterface {
    override fun getMoviesByName(type: String, name: String, page: Int, onResult: (error: Boolean, response: MovieList?, errorMessage: String?) -> Unit) {
       val call = RetrofitInitializer().service().getAllMovies(type = type, movieName = name, page = page)
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
}