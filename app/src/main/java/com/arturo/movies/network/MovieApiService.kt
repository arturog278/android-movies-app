package com.arturo.movies.network

import com.arturo.movies.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("movie/recommendations")
    suspend fun getRecommendedMovies(@Query("api_key") apiKey: String): MovieResponse

}