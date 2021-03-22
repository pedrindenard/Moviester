package com.app.moviester.database.dao

import androidx.room.*
import com.app.moviester.model.Movie
import kotlinx.coroutines.flow.Flow

// Salva, procura e deleta os filmes

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: Movie)

    @Query("SELECT * FROM Movie WHERE id = 1")
    fun findMovie(): Flow<List<Movie>>

    @Delete
    suspend fun deleteMovie(movie: Movie)
}