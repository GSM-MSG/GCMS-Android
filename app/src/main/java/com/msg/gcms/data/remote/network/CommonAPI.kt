package com.msg.gcms.data.remote.network

import com.msg.gcms.base.di.GCMSApplication
import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.auth.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.*

interface CommonAPI {

    @POST("auth/mobile")
    suspend fun postRegistration(
        @Body body: RegisterRequest
    ): Response<RegisterResponse>

    @POST("auth/verify")
    suspend fun postEmail(
        @Body body: CodeIssuanceRequest
    ): Response<Void>

    @HEAD("auth/verify")
    suspend fun headCheckCode(
        @Query("email") email: String,
        @Query("code") code: String
    ): Response<Void>

    @POST("auth/logout")
    suspend fun postLogout(): Response<Void>

    @POST("auth/refresh")
    suspend fun postRefresh(
        @Header("Authorization") refreshToken: String? = "Bearer ${GCMSApplication.prefs.refreshToken}"
    ): Response<RegisterResponse>
}
