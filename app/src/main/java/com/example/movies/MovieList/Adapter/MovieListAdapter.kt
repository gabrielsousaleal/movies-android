package com.example.movies.MovieList.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.Commons.Models.Movie
import com.example.movies.MovieList.ViewModel.MovieViewModel
import com.example.movies.R

interface MoviesRecyclerListener {
    fun pushNextPage()
}

class MovieListAdapter(private var movieList: ArrayList<Movie>, private val activityContext: Context, private val recyclerView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // MARK: - ViewHolders

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class MovieViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.movieName)
        val imageView: ImageView = itemView.findViewById(R.id.movieImageView)
    }

    // MARK: - Overrides

    override fun getItemId(position: Int): Long {
        val movie = movieList[position]
        return movie.id.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        val movie = movieList[position]
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
            val movie: Movie = movieList[position]
            populateMovieView(movie = movie, movieView = viewHolder as MovieViewHolder)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    // MARK: - Public Methods

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

    fun updateMovieList(movieList: ArrayList<Movie>) {
        this.movieList = movieList
    }

    // MARK: - Private Methods

    private fun populateMovieView(movie: Movie, movieView: MovieViewHolder) {
        val viewModel = MovieViewModel(movie = movie, activityContext = activityContext)

        movieView.nameTextView.text = viewModel.getName()
        recyclerView.post {
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
}

object VIEW_TYPE {
    const val MOVIE = 0
    const val LOADING_VIEW = 1
}