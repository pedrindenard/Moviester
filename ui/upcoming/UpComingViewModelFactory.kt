package com.app.moviester.ui.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.moviester.repository.MovieRepository

// Faz o inflate do ViewModel

@Suppress("UNCHECKED_CAST")
class UpComingViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UpComingViewModel(repository) as T
    }
}