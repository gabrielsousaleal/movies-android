package com.example.movies.Services.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/search/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun service(): RetrofitInterface {
        return retrofit.create(RetrofitInterface::class.java)
    }
}