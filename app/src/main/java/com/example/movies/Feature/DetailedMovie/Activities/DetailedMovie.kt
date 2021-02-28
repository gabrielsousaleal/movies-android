package com.example.movies.Feature.DetailedMovie.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movies.Commons.Models.Movie
import com.example.movies.Commons.ViewModel.IMAGE_SIZE
import com.example.movies.Commons.ViewModel.MovieViewModel
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