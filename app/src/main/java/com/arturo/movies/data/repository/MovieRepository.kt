package com.arturo.movies.data.repository

import android.util.Log
import com.arturo.movies.BuildConfig
import com.arturo.movies.data.local.dao.MovieDao
import com.arturo.movies.model.MovieEntity
import com.arturo.movies.network.MovieApiService
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao
) {

    suspend fun getMovies(): List<MovieEntity> {
        return try {
            val response = movieApiService.getPopularMovies(BuildConfig.TMDB_API_KEY)
            val movies: List<MovieEntity> = response.results.map { movie ->
                MovieEntity(
                    id = movie.id,
                    title = movie.title,
                    overview = movie.overview,
                    posterPath = movie.posterPath
                )
            }
            movieDao.insertMovies(movies)
            movies
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching movies: ${e.message}")
            movieDao.getAllMovies()
        }
    }
}