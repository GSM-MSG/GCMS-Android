package com.msg.gcms.data.remote.network

import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    @POST("auth")
    suspend fun postSignIn(
        @Body body: SignInRequest
    ): SignInResponse
}
