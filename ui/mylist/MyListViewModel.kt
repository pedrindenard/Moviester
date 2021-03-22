package com.app.moviester.ui.mylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.moviester.model.Movie
import com.app.moviester.repository.MovieRepository

// ViewModel

class MyListViewModel(repository: MovieRepository) : ViewModel() {

    val listMovieDB: LiveData<List<Movie>> = repository.findMovieDB.asLiveData()
}