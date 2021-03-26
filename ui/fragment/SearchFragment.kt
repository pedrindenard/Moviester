package com.app.moviester.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.moviester.R
import com.app.moviester.internet.model.Movie
import com.app.moviester.ui.adapter.MovieAdapter
import com.app.moviester.ui.viewmodel.MovieViewModel
import com.app.moviester.ui.viewmodel.MovieViewModelFactory
import com.app.moviester.util.MyApp
import com.app.moviester.util.Resource
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configAdapterRecyclerView()

        //Delay com coroutines, no search
        var job: Job? = null
        search_bar_movie.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(1000L)
                editable.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.getSearchMovie(editable.toString())
                    }
                }
            }
        }

        // Busca a pesquisa do filme no viewmodel
        viewModel.mResponseSearch.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { movies ->
                        movies.results.let { it1 -> adapter?.append(it1) }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "Erro ocorrido!: $message", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    // Oculta a barra de progresso
    private fun hideProgressBar() {
        progress_bar_search.visibility = View.INVISIBLE
    }

    // Mostra a barra de progresso
    private fun showProgressBar() {
        progress_bar_search.visibility = View.VISIBLE
    }

    // Define adapter e ação de click
    private fun configAdapterRecyclerView() {
        adapter?.onItemClickListener = {
            goToMovieDetails(it)
        }
        search_movie_list.adapter = adapter
        search_movie_list.layoutManager = LinearLayoutManager(context)
    }

    // Vai para o fragment Movie Details
    private fun goToMovieDetails(movie: Movie) {
        val direction = SearchFragmentDirections.actionNavigationSearchToMovieDetailsFragment(movie)
        controller.navigate(direction)
    }
}