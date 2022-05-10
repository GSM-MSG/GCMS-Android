package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.LoginRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.auth.response.LoginResponse
import com.msg.gcms.data.remote.dto.datasource.auth.response.RegisterResponse
import retrofit2.Response

interface CommonRepository {
    suspend fun postRegistration(
        body: RegisterRequest
    ): Response<RegisterResponse>

    suspend fun postLogin(
        body: LoginRequest
    ): Response<LoginResponse>
}
