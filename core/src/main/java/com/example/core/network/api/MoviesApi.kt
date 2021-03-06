package com.example.core.network.api

import com.example.core.data.movies.dto.MovieStateResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("movie/{movie_id}/account_states")
    fun getMovieAccountStates(
        @Path("movie_id") movieId: Int
    ): Single<MovieStateResponseDto>
}
