package com.example.feaure_authorization.network

import com.example.core.data.session.dto.CreateSessionRequestDto
import com.example.core.data.session.dto.CreateSessionResponseDto
import com.example.core.data.session.dto.RequestTokenResponseDto
import com.example.core.data.session.dto.ValidateTokenRequestDto
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @GET("authentication/token/new")
    fun getRequestToken(): Single<RequestTokenResponseDto>

    @POST("authentication/token/validate_with_login")
    fun validateRequestTokenWithLogin(@Body request: ValidateTokenRequestDto): Single<RequestTokenResponseDto>

    @POST("authentication/session/new")
    fun createSession(@Body request: CreateSessionRequestDto): Single<CreateSessionResponseDto>
}
