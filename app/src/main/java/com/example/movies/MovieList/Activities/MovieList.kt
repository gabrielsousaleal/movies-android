package com.example.movies.MovieList.Activities

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.Commons.Models.Movie
import com.example.movies.MovieList.Adapter.MovieListAdapter
import com.example.movies.MovieList.Adapter.MovieListScrollListener
import com.example.movies.MovieList.Adapter.MoviesRecyclerListener
import com.example.movies.MovieList.Adapter.VIEW_TYPE
import com.example.movies.MovieList.ViewModel.MOVIE_TYPE
import com.example.movies.MovieList.ViewModel.MovieListViewModel
import com.example.movies.R
import kotlinx.android.synthetic.main.activity_movie_list.*

interface MovieListActivityInterface {
    fun openMovieDetailed(movieID: Int)
}

class MovieList : AppCompatActivity(), MovieListActivityInterface {

    // MARK: - Private Properties

    private var alphaAnimation: AlphaAnimation? = null
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
            viewModel.setSearchType(MOVIE_TYPE.MOVIES) { error, response, errorMessage ->
                if (response != null) {
                    configureAdapter(response)
                }
            }
        }

        seriesButton.setOnClickListener {
            selectButton(seriesButton)
            viewModel.setSearchType(MOVIE_TYPE.SERIES) { error, response, errorMessage ->
                if (response != null) {
                    configureAdapter(response)
                }
            }
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
        selectButton(button = movieButton)
        viewModel.getMoviesByName(movieName = "naruto") { error, response, errorMessage ->
            if (response != null) {
                configureAdapter(movieList = response)
            }
        }
    }

    private fun configureAdapter(movieList: ArrayList<Movie>) {
        val adapter = MovieListAdapter(this, movieList, baseContext, recyclerView)
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

        val scrollListener = MovieListScrollListener(gridLayoutManager = recyclerView.layoutManager as GridLayoutManager)
        scrollListener.setRecyclerListener(object : MoviesRecyclerListener {
            override fun pushNextPage() {
                adapter.addLoadingView()
                Handler().postDelayed({
                    viewModel.loadNextPage { error, response, errorMessage ->
                        adapter.removeLoadingView()
                        if (response != null) {
                            adapter.updateMovieList(response)
                            scrollListener.stopLoading()
                        }
                    }
                }, 500)
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

    private fun startFullScreenLoading() {
        alphaAnimation = AlphaAnimation(0f, 3f)
        alphaAnimation?.duration = 200
        progressBarHolder.setAnimation(alphaAnimation)
        progressBarHolder.visibility = View.VISIBLE
    }

    private fun stopFullScreenLoading() {
        alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation?.duration = 200
        progressBarHolder.setAnimation(alphaAnimation)
        progressBarHolder.visibility = View.GONE
    }

    // MARK: - Interface Implementation

    override fun openMovieDetailed(movieID: Int) {
        startFullScreenLoading()
        viewModel.getDetaliedMovie(movieID = movieID) { error, response, errorMessage ->
            stopFullScreenLoading()
            if (response != null) {
                // PUSH NEXT PAGE
            }
        }
    }
}