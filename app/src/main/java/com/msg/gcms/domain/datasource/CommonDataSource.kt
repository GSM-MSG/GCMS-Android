package com.msg.gcms.domain.datasource

import com.msg.gcms.data.remote.dto.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse

interface CommonDataSource {
    suspend fun postRegistration(body: SignInRequest): SignInResponse

    suspend fun postEmail(body: CodeIssuanceRequest)

    suspend fun headCheckCode(email: String, code: String)

    suspend fun postLogout()

    suspend fun postRefresh(): SignInResponse
}
