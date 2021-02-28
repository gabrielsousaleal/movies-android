package com.example.movies.MovieList.ViewModel

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movies.Commons.Models.Movie
import com.example.movies.R
import com.example.movies.Services.Services
import com.example.movies.Services.ServicesInterface

class MovieViewModel(val activityContext: Context, val movie: Movie, val service: ServicesInterface = Services()) {

    //MARK: - Constants

    private val IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/w300"

    // MARK:- Public Methods

    fun getName(): String? {
        if (movie.movieName == null) {
            return movie.nameSerie
        }
        return movie.movieName
    }

    fun fetchImageIntoImageView(imageView: ImageView) {
        if (movie.posterPath == null) return

        val fullURL = IMAGE_BASE_PATH.plus(movie.posterPath)

        Glide.with(imageView.context)
                .load(fullURL)
                .apply(
                        RequestOptions()
                                .placeholder(createCircularLoading())
                                .fitCenter()
                )
                .into(imageView)
    }

    // MARK: - Private Methods

    private fun createCircularLoading(): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(activityContext)
        circularProgressDrawable.strokeWidth = 15f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.setColorSchemeColors(activityContext.resources.getColor(R.color.white))
        circularProgressDrawable.start()

        return circularProgressDrawable
    }
}