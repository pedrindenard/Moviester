package com.app.moviester.ui.upcoming

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.moviester.MyApp
import com.app.moviester.R
import com.app.moviester.ui.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_popular.*

class UpComingFragment : Fragment() {

    private val viewModel: UpComingViewModel by viewModels {
        UpComingViewModelFactory((activity?.application as MyApp).repository)
    }

    private val adapter by lazy {
        context?.let {
            MoviesAdapter(context = it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUpComingMovie()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_up_coming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configAdapterRecyclerView()
    }

    // Define adapter e ação de click
    private fun configAdapterRecyclerView() {
        up_coming_movie_list.adapter = adapter
        up_coming_movie_list.layoutManager = LinearLayoutManager(context)
    }

    // Busca filmes da lista Upcoming da API
    private fun getUpComingMovie() {
        viewModel.getUpComingMovie()
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
}