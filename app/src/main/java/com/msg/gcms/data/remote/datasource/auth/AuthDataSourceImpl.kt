package com.msg.gcms.data.remote.datasource.auth

import com.msg.gcms.data.remote.dto.auth.request.RefreshRequest
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse
import com.msg.gcms.data.remote.network.api.AuthAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val service: AuthAPI
) : AuthDataSource {
    override suspend fun postRegistration(body: SignInRequest): SignInResponse =
        GCMSApiHandler<SignInResponse>()
            .httpRequest { service.postSignIn(body = body) }
            .sendRequest()

    override suspend fun logout() =
        GCMSApiHandler<Unit>()
            .httpRequest { service.logout() }
            .sendRequest()

    override suspend fun refresh(header: String, body: RefreshRequest): SignInResponse =
        GCMSApiHandler<SignInResponse>()
            .httpRequest { service.refresh(header = header, body = body) }
            .sendRequest()
}
