package com.example.movies.Services.Retrofit

import com.example.movies.Commons.Models.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("search/{type}")
    fun getAllMovies (
            @Path("type") type: String,
            @Query("query") movieName: String,
            @Query("language") language: String = "pt-BR",
            @Query("api_key") api_key: String = "dcf373a212e3fd454f97f09a273a42e2",
            @Query("page") page: Int
    ): Call<MovieList>
}