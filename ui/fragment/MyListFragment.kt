package com.app.moviester.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.moviester.util.MyApp
import com.app.moviester.R
import com.app.moviester.internet.model.Movie
import com.app.moviester.ui.adapter.MyListAdapter
import com.app.moviester.ui.viewmodel.MovieViewModel
import com.app.moviester.ui.viewmodel.MovieViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_my_list.*

class MyListFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((activity?.application as MyApp).repository)
    }

    private val adapter by lazy {
        context?.let {
            MyListAdapter(context = it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMovieListDatabase()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.hide()
        return inflater.inflate(R.layout.fragment_my_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configMovieListDatabase()
    }

    // Configura Adapter
    private fun configMovieListDatabase() {
        adapter?.onItemClickListener = {
            configDialogAlertDeleteMovie(it)
        }
        list_movie_database.adapter = adapter
        list_movie_database.layoutManager = LinearLayoutManager(context)
    }

    // Busca os filmes na MyList
    private fun getMovieListDatabase() {
        viewModel.movieListLiveData.observe(this, {
            it?.let {
                it.sortedBy { it.title }
                adapter?.append(it)
            }
        })
    }

    // Deleta os filmes da MyList
    private fun configDialogAlertDeleteMovie(movie: Movie){
        context?.let {
            MaterialAlertDialogBuilder(it, R.style.AlertDialogTheme)
                .setTitle("Alerta de remoção!")
                .setMessage("Você deseja remover o filme ${movie.title} da sua lista?")
                .setPositiveButton("Confirmar") { dialog, _ ->
                    viewModel.deleteMovieList(movie)
                    adapter?.deleteMovieList(movie)
                    Toast.makeText(requireContext(),"${movie.title} removido!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNeutralButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
    }
}