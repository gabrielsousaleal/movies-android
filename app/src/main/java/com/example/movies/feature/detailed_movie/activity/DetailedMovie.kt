package com.example.movies.feature.detailed_movie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movies.commons.model.Movie
import com.example.movies.commons.view_model.IMAGE_SIZE
import com.example.movies.commons.view_model.MovieViewModel
import com.example.movies.R
import kotlinx.android.synthetic.main.activity_detailed_movie.*

class DetailedMovie() : AppCompatActivity() {

    // MARK: - Public Properties

    // MARK: - Private Properties

    // MARK: - Life Cicle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_movie)
        val movie = intent.getSerializableExtra("movie") as Movie
        val viewModel = MovieViewModel(baseContext, movie = movie)
        viewModel.fetchImageIntoImageView(imageSize = IMAGE_SIZE.HIGHT, imageView = imageView)
        overView.text = movie.overView
    }
}