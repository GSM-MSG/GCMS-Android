package com.msg.gcms.data.remote.datasource.auth

import com.msg.gcms.data.remote.dto.auth.request.RefreshRequest
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse

interface AuthDataSource {
    suspend fun postRegistration(body: SignInRequest): SignInResponse

    suspend fun logout()

    suspend fun refreshToken(header: String, body: RefreshRequest): SignInResponse
}
