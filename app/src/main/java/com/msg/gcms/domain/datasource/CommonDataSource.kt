package com.msg.gcms.domain.datasource

import com.msg.gcms.data.remote.dto.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.auth.response.RegisterResponse

interface CommonDataSource {
    suspend fun postRegistration(body: RegisterRequest): RegisterResponse

    suspend fun postEmail(body: CodeIssuanceRequest): Void

    suspend fun headCheckCode(email: String, code: String): Void

    suspend fun postLogout(): Void

    suspend fun postRefresh(): RegisterResponse
}
