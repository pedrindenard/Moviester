package com.app.moviester.extension

import androidx.appcompat.widget.AppCompatRatingBar

fun ratingBarConverter(ratingBar: AppCompatRatingBar, scoreBar: Float) {
    ratingBar.numStars = 5
    ratingBar.max = 5
    ratingBar.stepSize = 0.01F
    ratingBar.rating = ((scoreBar * 5) / 10)
}