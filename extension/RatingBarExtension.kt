package com.app.moviester.extension

import androidx.appcompat.widget.AppCompatRatingBar

fun appCompatRatingBar(movie: AppCompatRatingBar, rate: Float) {
    movie.numStars = 5
    movie.max = 5
    movie.stepSize = 0.01F
    movie.rating = ((rate * 5) / 10)
}