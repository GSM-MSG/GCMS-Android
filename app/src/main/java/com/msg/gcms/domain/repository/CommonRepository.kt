package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.auth.response.RegisterResponse

interface CommonRepository {
    suspend fun postRegistration(
        body: RegisterRequest
    ): RegisterResponse

    suspend fun postLogout()

    suspend fun postRefresh(): RegisterResponse
}
