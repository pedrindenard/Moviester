package com.app.moviester.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.moviester.util.MyApp
import com.app.moviester.R
import com.app.moviester.internet.model.Movie
import com.app.moviester.ui.adapter.MovieAdapter
import com.app.moviester.ui.viewmodel.MovieViewModel
import com.app.moviester.ui.viewmodel.MovieViewModelFactory
import kotlinx.android.synthetic.main.fragment_popular.*

class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((activity?.application as MyApp).repository)
    }

    // Adapter dos recyclerview
    private val adapter by lazy {
        context?.let {
            MovieAdapter(context = it)
        }
    }

    // Procura pra onde vai no navigation_graphic
    private val controller by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPopularMovie()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configAdapterRecyclerView()
   }

    // Define adapter e ação de click
    private fun configAdapterRecyclerView() {
        adapter?.onItemClickListener = {
            goToMovieDetails(it)
        }
        popular_movie_list.adapter = adapter
        popular_movie_list.layoutManager = LinearLayoutManager(context)
    }

    // Busca filmes da lista Popular da API
    private fun getPopularMovie() {
        viewModel.getPopularMovie()
        viewModel.mResponseMovie.observe(this, {
            if (it.isSuccessful) {
                it.body()?.let { movies ->
                    movies.results.let { it1 -> adapter?.append(it1) }
                }
            } else {
                Log.i("Error", it.errorBody().toString()) // Log de error
            }
        })
    }

    // Vai para o fragment Movie Details
    private fun goToMovieDetails(movie: Movie) {
        val direction =
            MovieFragmentDirections.actionNavigationPopularToMovieDetailsFragment(movie)
        controller.navigate(direction)
    }
}