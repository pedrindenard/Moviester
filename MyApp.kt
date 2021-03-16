package com.app.moviester

import android.app.Application
import com.app.moviester.repository.MovieRepository

open class MyApp : Application() {

    val repository by lazy { MovieRepository() }
}