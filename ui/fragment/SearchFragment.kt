package com.app.moviester.ui.fragment

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.moviester.MyApp
import com.app.moviester.R
import com.app.moviester.internet.model.Movie
import com.app.moviester.ui.adapter.MoviesAdapter
import com.app.moviester.ui.viewmodel.MovieViewModel
import com.app.moviester.ui.viewmodel.MovieViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((activity?.application as MyApp).repository)
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
        getPopularMovie()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configAppBarSearch(view)
        configAdapterRecyclerView()
    }

    // Define adapter e ação de click
    private fun configAdapterRecyclerView() {
        adapter?.onItemClickListener = {
            goToMovieDetails(it)
        }
        search_movie_list.adapter = adapter
        search_movie_list.layoutManager = LinearLayoutManager(context)
    }

    // Configura tollbar com as ações
    private fun configAppBarSearch(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.appBarSearch)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_search))
        val navHostFragment = NavHostFragment.findNavController(this)

        NavigationUI.setupWithNavController(toolbar, navHostFragment, appBarConfiguration)
        setHasOptionsMenu(true)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
    }

    // Configura função de pesquisa
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = activity?.getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu.findItem(R.id.search_movie).actionView as SearchView
        val searchMenuItem = menu.findItem(R.id.search_movie)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = "Pesquise um algo..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getSearchMovie(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getSearchMovie(newText)
                return true
            }
        })
        searchMenuItem.icon.setVisible(false, false)
    }

    private fun getSearchMovie(query: String?) {
        if (query?.length!! > 3) {
            viewModel.getSearchMovie(query)
            viewModel.mSearchResponse.observe(this@SearchFragment, {
                if (it.isSuccessful) {
                    it.body()?.let { result ->
                        adapter?.append(result.results)
                    }
                }
            })
        }
    }

    // Busca filmes da lista Popular da API
    private fun getPopularMovie() {
        viewModel.getPopularMovie()
        viewModel.mSearchResponse.observe(this, {
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
        val direction = SearchFragmentDirections.actionNavigationSearchToMovieDetailsFragment(movie)
        controller.navigate(direction)
    }
}