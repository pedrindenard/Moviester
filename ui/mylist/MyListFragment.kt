package com.app.moviester.ui.mylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.moviester.MyApp
import com.app.moviester.R
import com.app.moviester.ui.adapter.MyListAdapter
import kotlinx.android.synthetic.main.fragment_my_list.*

class MyListFragment : Fragment() {

    private val viewModel: MyListViewModel by viewModels {
        MyListViewModelFactory((activity?.application as MyApp).repository)
    }

    private val adapter by lazy {
        context?.let {
            MyListAdapter(context = it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMoviesDB()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configMyList()
    }

    // Configura Adapter
    private fun configMyList() {
        list_favorit.adapter = adapter
        list_favorit.layoutManager = LinearLayoutManager(context)
    }

    // Busca os filmes na lista
    private fun getMoviesDB() {
        viewModel.listMovieDB.observe(this, {
            it?.let {
                adapter?.append(it)
            }
        })
    }
}