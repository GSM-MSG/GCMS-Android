package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.auth.response.RegisterResponse
import com.msg.gcms.data.remote.network.CommonAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import com.msg.gcms.domain.datasource.CommonDataSource
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

    override suspend fun postEmail(body: CodeIssuanceRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.postEmail(body) }
            .sendRequest()
    }

    override suspend fun headCheckCode(email: String, code: String) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.headCheckCode(email, code) }
            .sendRequest()
    }

    override suspend fun postLogout() {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.postLogout() }
            .sendRequest()
    }

    override suspend fun postRefresh(): RegisterResponse {
        return GCMSApiHandler<RegisterResponse>()
            .httpRequest { service.postRefresh() }
            .sendRequest()
    }
}
