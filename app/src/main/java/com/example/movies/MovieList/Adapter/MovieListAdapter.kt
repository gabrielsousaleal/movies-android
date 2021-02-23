package com.example.movies.MovieList.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.Commons.Models.Movie
import com.example.movies.R

class MovieListAdapter (private val mMovies: List<Movie>) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.movieName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.movie_recycler_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: MovieListAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val movie: Movie = mMovies.get(position)
        // Set item views based on your views and data model
        val textView = viewHolder.nameTextView
        textView.setText(movie.name)
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mMovies.size
    }
}