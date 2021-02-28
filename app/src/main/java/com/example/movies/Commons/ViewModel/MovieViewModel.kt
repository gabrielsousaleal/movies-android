package com.example.movies.Commons.ViewModel

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movies.Commons.Models.Movie
import com.example.movies.R
import com.example.movies.Services.Services
import com.example.movies.Services.ServicesInterface

enum class IMAGE_SIZE(val string: String) {
    MEDIUM("w300/"),
    HIGHT("w500/")
}

class MovieViewModel(val activityContext: Context, var movie: Movie, val service: ServicesInterface = Services()) {

    //MARK: - Constants

    private val IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/"

    // MARK:- Public Methods

    fun getName(): String? {
        if (movie.movieName == null) {
            return movie.nameSerie
        }
        return movie.movieName
    }

    fun fetchImageIntoImageView(imageSize: IMAGE_SIZE, imageView: ImageView) {
        if (movie.posterPath == null) return

        val urlWithSize = IMAGE_BASE_PATH.plus(imageSize.string)
        val fullURL = urlWithSize.plus(movie.posterPath)

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