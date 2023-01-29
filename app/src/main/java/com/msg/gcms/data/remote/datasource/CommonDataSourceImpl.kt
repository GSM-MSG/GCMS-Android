package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.auth.response.RegisterResponse
import com.msg.gcms.data.remote.network.CommonAPI
import javax.inject.Inject

class CommonDataSourceImpl @Inject constructor(
    private val service: CommonAPI
) : CommonDataSource {
    override suspend fun postRegistration(body: RegisterRequest): RegisterResponse {
        return service.postRegistration(body)
    }

    override suspend fun postEmail(body: CodeIssuanceRequest): Void {
        return service.postEmail(body)
    }

    override suspend fun headCheckCode(email: String, code: String): Void {
        return service.headCheckCode(email, code)
    }

    override suspend fun postLogout(): Void {
        return service.postLogout()
    }

    override suspend fun postRefresh(): RegisterResponse {
        return service.postRefresh()
    }
}
