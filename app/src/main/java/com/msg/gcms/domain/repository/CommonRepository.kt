package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.auth.response.RegisterResponse
import retrofit2.Response

interface CommonRepository {
    suspend fun postRegistration(
        body: RegisterRequest
    ): Response<RegisterResponse>

    suspend fun postLogout(): Response<Void>

    suspend fun postRefresh(): Response<RegisterResponse>
}
