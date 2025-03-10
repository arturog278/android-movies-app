package com.arturo.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arturo.movies.data.local.dao.MovieDao
import com.arturo.movies.data.local.dao.UserDao
import com.arturo.movies.model.MovieEntity
import com.arturo.movies.model.UserEntity

@Database(entities = [MovieEntity::class, UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun userDao(): UserDao
}