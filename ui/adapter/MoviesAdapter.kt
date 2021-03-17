package com.app.moviester.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.moviester.R
import com.app.moviester.extension.appCompatRatingBar
import com.app.moviester.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.item_list.view.*

// Adapter Recyclerview

class MoviesAdapter(private val context: Context,
                    private val movies: MutableList<Movie> = mutableListOf(),
                    var onItemClickListener: (movie: Movie) -> Unit = {}
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_list, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun append(movie: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movie)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(View: View) : RecyclerView.ViewHolder(View) {
        private lateinit var movie: Movie

        private val moviePoster by lazy {
            View.item_poster
        }
        private val movieReleaseData by lazy {
            View.text_release_data_movie
        }
        private val movieDescription by lazy {
            View.text_description_movie
        }
        private val movieTitle by lazy {
            View.text_name_movie
        }
        private val movieRate by lazy {
            View.movieRate
        }
//        private val movieBudget by lazy {
//            View.
//        }
//        private val moviePopularity by lazy {
//            View.
//        }
//        private val movieStatus by lazy {
//            View.
//        }
//        private val movieVoteCount by lazy {
//            View.
//        }
//        private val movieRuntime by lazy {
//            View.
//        }
//        private val movieLanguage by lazy {
//            View.
//        }
//        private val movieRevenue by lazy {
//            View.
//        }

        init {
            View.setOnClickListener {
                if (::movie.isInitialized) {
                    onItemClickListener(movie)
                }
            }
        }

        fun bind(movie: Movie) {
            this.movie = movie
            movieTitle.text = movie.title
            movieDescription.text = movie.description
            movieReleaseData.text = movie.releaseDate
            appCompatRatingBar(movieRate, movie.rate)
            Glide.with(itemView)
                .load(movie.poster)
                .transform(CenterCrop())
                .into(moviePoster)
        }
    }
}