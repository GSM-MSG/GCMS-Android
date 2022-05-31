package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.LoginRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.auth.response.LoginResponse
import com.msg.gcms.data.remote.dto.datasource.auth.response.RegisterResponse
import com.msg.gcms.data.remote.network.CommonAPI
import retrofit2.Response
import javax.inject.Inject

class CommonDataSourceImpl @Inject constructor(
    private val service: CommonAPI
) : CommonDataSource {
    override suspend fun postRegistration(body: RegisterRequest): Response<RegisterResponse> {
        return service.postRegistration(body)
    }

    override suspend fun postEmail(body: CodeIssuanceRequest): Response<Void> {
        return service.postEmail(body)
    }

    override suspend fun headCheckCode(email: String, code: String): Response<Void> {
        return service.headCheckCode(email, code)
    }

    override suspend fun postLogin(body: LoginRequest): Response<LoginResponse> {
        return service.postLogin(body)
    }

    override suspend fun postLogout(): Response<Void> {
        return service.postLogout()
    }

    override suspend fun checkLogin(): Response<Void> {
        return service.checkLogin()
    }
}
