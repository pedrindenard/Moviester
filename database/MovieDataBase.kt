package com.app.moviester.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.moviester.database.dao.MovieDAO
import com.app.moviester.model.Movie

// Criando o DataBase e atribuindo a classe Movie
@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun MovieDAO(): MovieDAO

    companion object {
        @Volatile
        private var INSTANCE: MovieDataBase? = null

        // Instanciando o banco de dados criado
        fun getMovieDataBase(context: Context): MovieDataBase {
            return INSTANCE ?: synchronized(this) {
                val dataBase = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDataBase::class.java,
                    "movie-db"
                ).build()
                INSTANCE = dataBase
                dataBase
            }
        }
    }
}