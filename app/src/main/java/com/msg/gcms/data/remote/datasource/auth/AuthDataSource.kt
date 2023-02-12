package com.msg.gcms.data.remote.datasource.auth

import com.msg.gcms.data.remote.dto.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse

interface AuthDataSource {
    suspend fun postRegistration(body: SignInRequest): SignInResponse

    suspend fun postEmail(body: CodeIssuanceRequest)

    suspend fun headCheckCode(email: String, code: String)

    suspend fun postLogout()

    suspend fun postRefresh(): SignInResponse
}
