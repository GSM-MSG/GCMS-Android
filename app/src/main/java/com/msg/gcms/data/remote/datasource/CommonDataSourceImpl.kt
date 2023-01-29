package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.auth.response.RegisterResponse
import com.msg.gcms.data.remote.network.CommonAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import javax.inject.Inject

class CommonDataSourceImpl @Inject constructor(
    private val service: CommonAPI
) : CommonDataSource {
    override suspend fun postRegistration(body: RegisterRequest): RegisterResponse {
        return GCMSApiHandler<RegisterResponse>().
        httpRequest {
            service.postRegistration(body)
        }.sendRequest()
    }

    override suspend fun postEmail(body: CodeIssuanceRequest): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.postEmail(body) }
            .sendRequest()
    }

    override suspend fun headCheckCode(email: String, code: String): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.headCheckCode(email, code) }
            .sendRequest()
    }

    override suspend fun postLogout(): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.postLogout() }
            .sendRequest()
    }

    override suspend fun postRefresh(): RegisterResponse {
        return GCMSApiHandler<RegisterResponse>()
            .httpRequest { service.postRefresh() }
            .sendRequest()
    }
}
