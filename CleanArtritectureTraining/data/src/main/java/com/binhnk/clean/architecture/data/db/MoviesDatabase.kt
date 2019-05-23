package com.binhnk.clean.architecture.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.binhnk.clean.architecture.data.entities.MovieData

@Database(entities = [MovieData::class], version = 1)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
}