package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse

interface AuthRepository {
    suspend fun postRegistration(
        body: SignInRequest
    ): SignInResponse

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun getAccessExp(): String

    suspend fun getRefreshExp(): String

    suspend fun saveTokenInfo(accessToken: String, refreshToken: String, accessExp: String, refreshExp: String)
}
