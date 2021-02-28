package com.example.movies.MovieList.Activities

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.Commons.Models.Movie
import com.example.movies.MovieList.Adapter.MovieListAdapter
import com.example.movies.MovieList.Adapter.MovieListScrollListener
import com.example.movies.MovieList.Adapter.MoviesRecyclerListener
import com.example.movies.MovieList.Adapter.VIEW_TYPE
import com.example.movies.MovieList.ViewModel.MovieListViewModel
import com.example.movies.R
import kotlinx.android.synthetic.main.activity_movie_list.*
import okhttp3.internal.wait
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class MovieList : AppCompatActivity() {

    // MARK: - Private Properties

    private val viewModel = MovieListViewModel()

    // MARK: - Life Cicle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        configureSearchView()
        setDefaultAdapter()
        configureButtons()
    }

    // MARK: - Private Methods

    private fun configureButtons() {
        movieButton.setOnClickListener {
            selectButton(movieButton)
        }

        seriesButton.setOnClickListener {
            selectButton(seriesButton)
        }
    }

    private fun selectButton(button: Button) {
        diselectAllButtons()
        button.setBackgroundColor(resources.getColor(R.color.purple_700))
    }

    private fun diselectAllButtons() {
        seriesButton.setBackgroundColor(resources.getColor(R.color.transparent))
        movieButton.setBackgroundColor(resources.getColor(R.color.transparent))
    }

    private fun setDefaultAdapter() {
        viewModel.getMoviesByName(movieName = "naruto") { error, response, errorMessage ->
            if (response != null) {
                configureAdapter(movieList = response)
            }
        }
    }

    private fun configureAdapter(movieList: ArrayList<Movie>) {
        val adapter = MovieListAdapter(movieList, this, recyclerView)
        recyclerView.adapter = adapter
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    VIEW_TYPE.MOVIE -> 1
                    VIEW_TYPE.LOADING_VIEW -> 2
                    else -> -1
                }
            }
        }

        val scrollListener = MovieListScrollListener(context = baseContext ,gridLayoutManager = recyclerView.layoutManager as GridLayoutManager)
        scrollListener.setRecyclerListener(object : MoviesRecyclerListener {
            override fun pushNextPage() {
                adapter.addLoadingView()
                Handler().postDelayed( {
                    viewModel.loadNextPage { error, response, errorMessage ->
                        adapter.removeLoadingView()
                        if (response != null) {
                            adapter.updateMovieList(response)
                            scrollListener.stopLoading()
                        }
                    }
                }, 2000)
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