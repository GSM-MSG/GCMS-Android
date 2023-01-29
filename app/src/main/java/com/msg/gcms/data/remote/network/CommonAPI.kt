package com.msg.gcms.data.remote.network

import com.msg.gcms.base.di.GCMSApplication
import com.msg.gcms.data.remote.dto.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.auth.response.RegisterResponse
import retrofit2.http.*

interface CommonAPI {

    @POST("auth/mobile")
    suspend fun postRegistration(
        @Body body: RegisterRequest
    ): RegisterResponse

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
    ): RegisterResponse
}
