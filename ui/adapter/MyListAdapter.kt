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
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_mylist.view.*

class MyListAdapter(private val context: Context,
                    private val movies: MutableList<Movie> = mutableListOf(),
                    var onItemClickListener: (movie: Movie) -> Unit = {}
) : RecyclerView.Adapter<MyListAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_mylist, parent, false)
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
            View.item_backdrop
        }
        private val movieTitle by lazy {
            View.text_name_movie
        }
        private val movieDescription by lazy {
            View.text_description_movie
        }
        private val movieRate by lazy {
            View.text_movie_details_rate_mylist
        }
        private val movieRuntime by lazy {
            View.text_movie_details_runtime_mylist
        }
        private val movieDate by lazy {
            View.text_movie_details_release_date
        }

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
            appCompatRatingBar(movieRate, movie.rate)
            movieDate.text = movie.releaseDate
            movieRuntime.text = movie.runtime.toString()

            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+movie.poster)
                .placeholder(R.drawable.ic_movie_error)
                .into(moviePoster)
        }
    }
}