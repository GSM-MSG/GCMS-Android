package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse

interface CommonRepository {
    suspend fun postRegistration(
        body: SignInRequest
    ): SignInResponse

    suspend fun postLogout()

    suspend fun postRefresh(): SignInResponse
}
