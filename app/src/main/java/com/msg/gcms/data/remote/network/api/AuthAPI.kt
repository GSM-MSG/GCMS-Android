package com.msg.gcms.data.remote.network.api

import com.msg.gcms.di.GCMSApplication
import com.msg.gcms.data.remote.dto.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse
import retrofit2.http.*

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
        @Header("Authorization") refreshToken: String? = "Bearer ${GCMSApplication.prefs.refreshToken}"
    ): SignInResponse
}
