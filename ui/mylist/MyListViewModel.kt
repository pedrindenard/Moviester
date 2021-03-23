package com.app.moviester.ui.mylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.moviester.model.Movie
import com.app.moviester.repository.MovieRepository
import kotlinx.coroutines.launch

// ViewModel

class MyListViewModel(private val repository: MovieRepository) : ViewModel() {

    val movieListLiveData: LiveData<List<Movie>> = repository.movieListDatabase.asLiveData()

    fun deleteMovieList(movie: Movie){
        viewModelScope.launch {
            repository.deleteMovieList(movie)
        }
    }
}