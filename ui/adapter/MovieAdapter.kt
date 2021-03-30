package com.app.moviester.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.moviester.R
import com.app.moviester.internet.model.Movie
import com.app.moviester.ui.adapter.MovieAdapter.MovieViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_list.view.*

// Adapter Recyclerview

class MovieAdapter(private val context: Context,
                   private val movies: MutableList<Movie> = mutableListOf(),
                   var onItemClickListener: (movie: Movie) -> Unit = {},
) : RecyclerView.Adapter<MovieViewHolder>() {

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

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var movie: Movie

        private val movieBackdrop by lazy {
            view.item_backdrop
        }
        private val movieDescription by lazy {
            view.text_description_movie
        }
        private val movieTitle by lazy {
            view.text_name_movie
        }

        init {
            view.setOnClickListener {
                if(::movie.isInitialized) {
                    onItemClickListener(movie)
                }
            }
        }

        fun bind(movie: Movie) {
            this.movie = movie
            movieTitle.text = movie.title
            movieDescription.text = movie.description
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+movie.backdrop)
                .placeholder(R.drawable.ic_error)
                .into(movieBackdrop)
        }
    }


}