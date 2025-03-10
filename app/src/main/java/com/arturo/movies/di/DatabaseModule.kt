package com.arturo.movies.di

import android.content.Context
import androidx.room.Room
import com.arturo.movies.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movies_database"
        ).build()
    }

    @Provides
    fun provideMovieDao(database: AppDatabase) = database.movieDao()

    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()

}