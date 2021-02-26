package com.example.movies.MovieList.Adapter

import android.content.Context
import android.graphics.ColorFilter
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.movies.Commons.Models.Movie
import com.example.movies.R

interface MoviesRecyclerListener {
    fun pushNextPage()
}

class MovieListAdapter(private var movieList: List<Movie>, private val activityContext: Context) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.movieName)
        val imageView = itemView.findViewById<ImageView>(R.id.movieImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.movie_recycler_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: MovieListAdapter.ViewHolder, position: Int) {
        val movie: Movie = movieList.get(position)
        val textView = viewHolder.nameTextView
        textView.setText(movie.name)
        setImage(imageView = viewHolder.imageView, posterPath = movie.posterPath)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    private fun setImage(imageView: ImageView, posterPath: String) {
        if (posterPath == null) return

        val baseURL = "http://image.tmdb.org/t/p/w300"
        val fullURL = baseURL.plus(posterPath)

        val circularProgressDrawable = CircularProgressDrawable(activityContext)
        circularProgressDrawable.strokeWidth = 15f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.setColorSchemeColors(activityContext.resources.getColor(R.color.white))
        circularProgressDrawable.start()

        Glide.with(imageView.context)
                .load(fullURL)
                .apply(
                        RequestOptions()
                                .placeholder(circularProgressDrawable)
                                .fitCenter()
                )
                .into(imageView)
    }

    fun updateMovieList(movieList: List<Movie>) {
        this.movieList = movieList
        Toast.makeText(activityContext, "${movieList.count()}", Toast.LENGTH_SHORT).show()
        notifyDataSetChanged()
    }
}