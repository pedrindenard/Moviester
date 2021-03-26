package com.app.moviester.util

import android.app.Application
import com.app.moviester.database.MovieDataBase
import com.app.moviester.internet.repository.MovieRepository

open class MyApp : Application() {

    private val database by lazy { MovieDataBase.getMovieDataBase(this) }
    val repository by lazy { MovieRepository(database.MovieDAO()) }
}