package com.app.moviester.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.moviester.R
import com.app.moviester.extension.ratingBarConverter
import com.app.moviester.internet.model.Movie
import com.app.moviester.ui.adapter.MyListAdapter.MyListViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_mylist.view.*

// Adapter Recyclerview

class MyListAdapter(private val context: Context,
                    private val movies: MutableList<Movie> = mutableListOf(),
                    var onItemClickListener: (movie: Movie) -> Unit = {}
) : RecyclerView.Adapter<MyListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_mylist, parent, false)
        return MyListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun append(movie: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movie)
        notifyDataSetChanged()
    }

    fun deleteMovieList(movie: Movie){
        this.movies.removeAt(movies.indexOf(movie))
        notifyItemRemoved(movies.indexOf(movie))
    }

    inner class MyListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var movie: Movie

        private val moviePoster by lazy {
            view.image_poster
        }
        private val movieTitle by lazy {
            view.text_name_movie_mylist
        }
        private val movieDescription by lazy {
            view.text_description_mylist
        }
        private val movieRatingBar by lazy {
            view.text_movie_details_rate_mylist
        }
        private val movieReleaseDate by lazy {
            view.text_movie_details_release_date_mylist
        }
        private val movieDelete by lazy {
            view.textDeleteFavorite
        }

        init {
            movieDelete?.let {
                it.setOnClickListener {
                    if(::movie.isInitialized){
                        onItemClickListener(movie)
                    }
                }
            }
        }

        fun bind(movie: Movie) {
            this.movie = movie
            movieTitle.text = movie.title
            movieDescription.text = movie.description
            ratingBarConverter(movieRatingBar, movie.rate)
            movieReleaseDate.text = movie.releaseDate

            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+movie.poster)
                .placeholder(R.drawable.ic_error)
                .into(moviePoster)
        }
    }
}