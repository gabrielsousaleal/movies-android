package com.example.movies.MovieList.Adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MovieListScrollListener(private var gridLayoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {

    // MARK: - Private Properties

    private lateinit var recyclerListener: MoviesRecyclerListener
    private var isLoading = false

    // MARK: - Life Cycle

    fun setRecyclerListener(recyclerListener: MoviesRecyclerListener) {
        this.recyclerListener = recyclerListener
    }

    fun stopLoading() {
        isLoading = false
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy <= 0) return

        val totalItensCount = gridLayoutManager.itemCount
        val lastSeenItemPosition = gridLayoutManager.findLastVisibleItemPosition()

        if (!isLoading && totalItensCount <= lastSeenItemPosition + gridLayoutManager.spanCount) {
            isLoading = true
            recyclerListener.pushNextPage()
        }
    }
}