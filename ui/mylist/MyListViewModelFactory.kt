package com.app.moviester.ui.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.moviester.repository.MovieRepository

// Faz o inflate do ViewModel

@Suppress("UNCHECKED_CAST")
class MyListViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyListViewModel(repository) as T
    }
}
