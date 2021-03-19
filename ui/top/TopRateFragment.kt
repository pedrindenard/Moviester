package com.app.moviester.ui.top

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.moviester.MyApp
import com.app.moviester.R
import com.app.moviester.model.Movie
import com.app.moviester.ui.adapter.MoviesAdapter
import com.app.moviester.ui.popular.PopularFragmentDirections
import kotlinx.android.synthetic.main.fragment_top_rate.*

class TopRateFragment : Fragment() {

    private val viewModel: TopRateViewModel by viewModels {
        TopRateViewModelFactory((activity?.application as MyApp).repository)
    }

    // Adapter dos recyclerview
    private val adapter by lazy {
        context?.let {
            MoviesAdapter(context = it)
        }
    }

    // Procura pra onde vai no navigation_graphic
    private val controller by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getTopRateMovie()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top_rate, container, false)
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
        top_rate_movie_list.adapter = adapter
        top_rate_movie_list.layoutManager = LinearLayoutManager(context)
    }

    // Busca filmes da lista TopRate da API
    private fun getTopRateMovie() {
        viewModel.getTopRateMovie()
        viewModel.mResponse.observe(this, {
            if (it.isSuccessful) {
                it.body()?.let { movies ->
                    movies.results?.let { it1 -> adapter?.append(it1) }
                }
            } else {
                Log.i("Error", it.errorBody().toString()) // Log de error
            }
        })
    }

    // Vai para o fragment Movie Details
    private fun goToMovieDetails(movie: Movie) {
        val direction = PopularFragmentDirections
            .actionNavigationPopularToMovieDetailsFragment(movie)
        controller.navigate(direction)
    }
}