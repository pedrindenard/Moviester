package com.app.moviester.database

import androidx.room.*
import com.app.moviester.internet.model.Movie
import kotlinx.coroutines.flow.Flow
import androidx.room.OnConflictStrategy.REPLACE

// Salva, procura e deleta os filmes

@Dao
interface MovieDAO {

    @Insert(onConflict = REPLACE)
    suspend fun saveMovieList(movie: Movie)

    @Query("SELECT * FROM Movie WHERE id")
    fun findMovieList(): Flow<List<Movie>>

    @Delete
    suspend fun deleteMovieList(movie: Movie)
}