package com.example.feature_movieslist.data.network

import com.example.feature_movieslist.data.network.dto.MoviesResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface FavoritesApi {

    @GET("account/{account_id}/favorite/movies")
    fun getFavorites(@Path("account_id") accountId: Int): Single<MoviesResponseDto>
}
