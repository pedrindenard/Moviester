package com.app.moviester

import android.app.Application
import com.app.moviester.database.MovieDB
import com.app.moviester.repository.MovieRepository

open class MyApp : Application() {

    private val database by lazy { MovieDB.getMovieDB(this) }
    val repository by lazy { MovieRepository(database.MovieDAO()) }
}