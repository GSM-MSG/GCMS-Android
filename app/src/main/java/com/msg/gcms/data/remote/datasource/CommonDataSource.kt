package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.LoginRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.auth.response.LoginResponse
import com.msg.gcms.data.remote.dto.datasource.auth.response.RegisterResponse
import retrofit2.Response

interface CommonDataSource {
    suspend fun postRegistration(body: RegisterRequest): Response<RegisterResponse>

    suspend fun postEmail(body: CodeIssuanceRequest): Response<Void>

    suspend fun headCheckCode(email: String, code: String): Response<Void>

    suspend fun postLogin(body: LoginRequest): Response<LoginResponse>
}
