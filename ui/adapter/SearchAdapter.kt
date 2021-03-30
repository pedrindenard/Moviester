package com.app.moviester.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.moviester.R
import com.app.moviester.internet.model.Movie
import com.app.moviester.ui.adapter.SearchAdapter.PosterViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_poster.view.*

// Adapter Recyclerview

class SearchAdapter(private val context: Context,
                    private val movies: MutableList<Movie> = mutableListOf(),
                    var onItemClickListener: (movie: Movie) -> Unit = {}
) : RecyclerView.Adapter<PosterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_poster, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun append(movie: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movie)
        notifyDataSetChanged()
    }

    inner class PosterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var movie: Movie

        private val moviePoster by lazy {
            view.item_poster
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
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+movie.poster)
                .placeholder(R.drawable.ic_error)
                .into(moviePoster)
        }
    }
}