package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import retrofit2.Response

interface CommonRepository {
    suspend fun postRegistration(
        body : RegisterRequest
    ): Response<Void>

    suspend fun postEmail(
        body: CodeIssuanceRequest
    ): Response<Void>

    suspend fun headCheckCode(
        email: String,
        code: String
    ): Response<Void>
}