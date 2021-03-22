package com.app.moviester.ui.details

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.app.moviester.MyApp
import com.app.moviester.R
import com.app.moviester.extension.appCompatRatingBar
import com.app.moviester.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import retrofit2.Response

class MovieDetailsFragment : Fragment() {

    private var index = 0

    private val lists = arrayOf("Filmes favoritados")

    private val argument by navArgs<MovieDetailsFragmentArgs>()

    private val movie by lazy {
        argument.movie
    }

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory((activity?.application as MyApp).repository)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovieDetails()
        addMovieDB()
        configAppBarMovieDetails(view)
    }

    // Informações na tela
    private fun getMovieDetails() {
        viewModel.getMovieDetails(movie.id)
        viewModel.mResponse.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                topAppBarTitle.title = "Detalhes do filme"
                text_movie_details_release_date.text = it.body()?.releaseDate
                text_movie_details_runtime_mylist.text = it.body()?.runtime.toString()
                text_movie_details_title.text = it.body()?.title
                text_movie_details_description.text = it.body()?.description
                getScore(it)
                getPoster(it)
                getBackdrop(it)
            }
        })
    }

    // Obtem RatingBar
    private fun getScore(movieDetails: Response<Movie>) {
        val rate = movieDetails.body()!!.rate
        appCompatRatingBar(text_movie_star, rate)
    }

    // Obtem Imagem
    private fun getPoster(MovieDetails: Response<Movie>) {
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500"+MovieDetails.body()?.poster)
            .transform(CenterCrop())
            .into(imagePoster)
    }

    private fun getBackdrop(MovieDetails: Response<Movie>) {
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/original" + MovieDetails.body()?.backdrop)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(50)))
            .into(imageDetail)
    }

    // Configura a AppBar com a funcionalidade de acionar um filme
    private fun configAppBarMovieDetails(view: View) {
        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBarTitle)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHostFragment)

        topAppBarTitle.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_movie_to_mylist -> {
                    configDialogAddMovie()
                    true
                }
                else -> false
            }
        }
    }

    // Configura dialog para adiconar filme
    private fun configDialogAddMovie() {
        var selectItem = lists[index]

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.add_movie_to_mylist)
            .setSingleChoiceItems(lists, index) { _, which ->
                index = which
                selectItem = lists[which]
            }
            .setPositiveButton("Salvar filme") { dialog, _ ->
                if (selectItem == lists[0]) {
                    addMovieDB()
                }
                dialog.dismiss()
            }
            .setNeutralButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    // Adiciona o filme na lista
    private fun addMovieDB() {
        viewModel.saveMovieDB(movie)
    }
}