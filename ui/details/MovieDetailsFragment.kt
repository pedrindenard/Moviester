package com.app.moviester.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.app.moviester.MyApp
import com.app.moviester.R
import com.app.moviester.extension.appCompatRatingBar
import com.app.moviester.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import retrofit2.Response

class MovieDetailsFragment : Fragment() {

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
    }

    // Informações na tela
    private fun getMovieDetails() {
        viewModel.getMovieDetails(movie.id)
        viewModel.mResponse.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                text_movie_details_release_date.text = it.body()?.releaseDate
                text_movie_details_runtime.text = it.body()?.runtime.toString()
                text_movie_details_title.text = it.body()?.title
                text_movie_details_description.text = it.body()?.description
                textView2.text = it.body()?.language
                textView3.text = it.body()?.status
                textView8.text = it.body()?.budget.toString()
                textView5.text = it.body()?.popularity.toString()
                textView6.text = it.body()?.revenue.toString()
                textView7.text = it.body()?.vote_count.toString()
                getScore(it)
                getPoster(it)
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
}