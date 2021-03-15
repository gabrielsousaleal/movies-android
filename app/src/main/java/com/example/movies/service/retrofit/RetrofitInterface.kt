package com.example.movies.service.retrofit

import com.example.movies.commons.model.Movie
import com.example.movies.commons.model.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("search/{type}")
    fun getAllMovies (
            @Path("type") type: String,
            @Query("query") movieName: String,
            @Query("language") language: String,
            @Query("api_key") api_key: String,
            @Query("page") page: Int
    ): Call<MovieList>

    @GET("{type}/{movieID}")
    fun getDetaliedMovie (
            @Path("type") type: String,
            @Path("movieID") movieID: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Call<Movie>
}