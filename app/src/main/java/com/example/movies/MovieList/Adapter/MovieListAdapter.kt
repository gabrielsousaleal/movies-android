package com.example.movies.MovieList.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movies.Commons.Models.Movie
import com.example.movies.MovieList.ViewModel.MovieViewModel
import com.example.movies.R

interface MoviesRecyclerListener {
    fun pushNextPage()
}

class MovieListAdapter(private var movieList: ArrayList<Movie>, private val activityContext: Context, private val recyclerView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class MovieViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.movieName)
        val imageView = itemView.findViewById<ImageView>(R.id.movieImageView)
    }

    override fun getItemId(position: Int): Long {
        val movie: Movie = movieList.get(position)
        return movie.id as Long
    }

    override fun getItemViewType(position: Int): Int {
        val movie = movieList.get(position)
        return if (movie.id == -1) {
            VIEW_TYPE.LOADING_VIEW
        } else {
            VIEW_TYPE.MOVIE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewForRow(parent = parent, viewType = viewType)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder.itemViewType == VIEW_TYPE.MOVIE) {
            val movie: Movie = movieList.get(position)
            populateMovieView(movie = movie, movieView = viewHolder as MovieViewHolder)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun addLoadingView() {
        recyclerView.post {
            val fakeMovie = Movie()
            movieList.add(fakeMovie)
            notifyItemInserted(movieList.size - 1)
        }
    }

    fun removeLoadingView() {
        recyclerView.post {
            movieList.removeAll { movie ->
                movie.id == -1
            }
            notifyItemRemoved(movieList.size - 1)
        }
    }

    // MARK: - Private Methods

    private fun populateMovieView(movie: Movie, movieView: MovieViewHolder) {
        val viewModel = MovieViewModel(movie = movie, activityContext = activityContext)

        movieView.nameTextView.setText(viewModel.getName())
        recyclerView.post() {
           viewModel.fetchImageIntoImageView(imageView = movieView.imageView)
        }
    }

    private fun getViewForRow(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflateView = getInflateView(parent = parent, viewType = viewType)
        return when (viewType) {
            VIEW_TYPE.MOVIE -> MovieViewHolder(inflateView)
            VIEW_TYPE.LOADING_VIEW -> LoadingViewHolder(inflateView)
            else -> MovieViewHolder(inflateView)
        }
    }

    private fun getInflateView(parent: ViewGroup, viewType: Int): View {
        val viewId = when(viewType) {
            VIEW_TYPE.MOVIE ->
                R.layout.movie_recycler_item
            VIEW_TYPE.LOADING_VIEW ->
                R.layout.scroll_loading
            else -> R.layout.movie_recycler_item
        }

        return LayoutInflater.from(parent.context).inflate(viewId, parent, false)
    }

    fun updateMovieList(movieList: ArrayList<Movie>) {
        this.movieList = movieList
    }
}

object VIEW_TYPE {
    const val MOVIE = 0
    const val LOADING_VIEW = 1
}