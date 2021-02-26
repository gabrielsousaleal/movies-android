package com.example.movies.MovieList.Adapter

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MovieListScrollListener(private val context: Context, private var gridLayoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {

    // MARK: - Private Properties

    private lateinit var recyclerListener: MoviesRecyclerListener

    // MARK: - Life Cycle

    fun setRecyclerListener(recyclerListener: MoviesRecyclerListener) {
        this.recyclerListener = recyclerListener
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy <= 0) return

        val totalItensCount = gridLayoutManager.itemCount
        val lastSeenItemPosition = gridLayoutManager.findLastVisibleItemPosition()

        if (totalItensCount <= lastSeenItemPosition + gridLayoutManager.spanCount) {
            recyclerListener.pushNextPage()
        }
    }
}