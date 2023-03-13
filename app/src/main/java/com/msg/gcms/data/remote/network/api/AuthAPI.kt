package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.auth.request.RefreshRequest
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthAPI {

    @POST("auth")
    suspend fun postSignIn(
        @Body body: SignInRequest
    ): SignInResponse

    @DELETE("auth")
    suspend fun logout(): Response<Unit>

    @PATCH("auth")
    suspend fun refreshToken(
        @Header("Refresh-Token") header: String,
        @Body body: RefreshRequest
    ): SignInResponse
}
