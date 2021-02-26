package com.example.movies.MovieList.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.Commons.Models.Movie
import com.example.movies.MovieList.Adapter.MovieListAdapter
import com.example.movies.MovieList.Adapter.MovieListScrollListener
import com.example.movies.MovieList.Adapter.MoviesRecyclerListener
import com.example.movies.MovieList.ViewModel.MovieListViewModel
import com.example.movies.R
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieList : AppCompatActivity() {

    // MARK: - Private Properties

    private val viewModel = MovieListViewModel()

    // MARK: - Life Cicle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        configureSearchView()
        setDefaultAdapter()
    }

    // MARK: - Private Methods

    private fun setDefaultAdapter() {
        viewModel.getMoviesByName(movieName = "naruto") { error, response, errorMessage ->
            if (response != null) {
                configureAdapter(movieList = response)
            }
        }
    }

    private fun configureAdapter(movieList: List<Movie>) {
        val adapter = MovieListAdapter(movieList, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val scrollListener = MovieListScrollListener(context = baseContext ,gridLayoutManager = recyclerView.layoutManager as GridLayoutManager)
        scrollListener.setRecyclerListener(object : MoviesRecyclerListener {
            override fun pushNextPage() {
                viewModel.loadNextPage { error, response, errorMessage ->
                        if (errorMessage != null) {
                        }
                    if (response != null) {
                        adapter.updateMovieList(response)
                        recyclerView.post {
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })

        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun configureSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.getMoviesByName(movieName = newText) { error, response, errorMessage ->
                    if (response != null) {
                        configureAdapter(movieList = response)
                    }
                }
                return false
            }
        })
    }
}