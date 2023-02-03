package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse
import com.msg.gcms.data.remote.network.AuthAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val service: AuthAPI
) : AuthDataSource {
    override suspend fun postRegistration(body: SignInRequest): SignInResponse {
        return GCMSApiHandler<SignInResponse>().httpRequest {
            service.postSignIn(body)
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

    override suspend fun postRefresh(): SignInResponse {
        return GCMSApiHandler<SignInResponse>()
            .httpRequest { service.postRefresh() }
            .sendRequest()
    }
}
