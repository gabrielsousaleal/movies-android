package com.example.movies.MovieList.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.Commons.Models.Movie
import com.example.movies.MovieList.Adapter.MovieListAdapter
import com.example.movies.MovieList.ViewModel.MovieListViewModel
import com.example.movies.R
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieList : AppCompatActivity() {

    private val viewModel = MovieListViewModel()
    private var movieName = "suco"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        viewModel.getMoviesByName(movieName = movieName) { error, response, errorMessage ->
            if (response != null) {
                configureAdapter(movieList = response)
            }
        }

    }

    fun configureAdapter(movieList: List<Movie>) {
        val adapter = MovieListAdapter(movieList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}