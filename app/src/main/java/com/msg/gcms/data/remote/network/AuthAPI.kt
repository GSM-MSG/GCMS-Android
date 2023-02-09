package com.msg.gcms.data.remote.network

import com.msg.gcms.data.remote.dto.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse
import retrofit2.http.Body
import retrofit2.http.HEAD
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPI {

    @POST("auth")
    suspend fun postSignIn(
        @Body body: SignInRequest
    ): SignInResponse

    @POST("auth/verify")
    suspend fun postEmail(
        @Body body: CodeIssuanceRequest
    )

    @HEAD("auth/verify")
    suspend fun headCheckCode(
        @Query("email") email: String,
        @Query("code") code: String
    )

    @POST("auth/logout")
    suspend fun postLogout()

    @POST("auth/refresh")
    suspend fun postRefresh(
        @Header("Authorization") refreshToken: String? = "Bearer "
    ): SignInResponse
}
