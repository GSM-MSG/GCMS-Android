package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.auth.AuthDataSourceImpl
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse
import com.msg.gcms.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val datasource: AuthDataSourceImpl,
) : AuthRepository {
    override suspend fun postRegistration(body: SignInRequest): SignInResponse {
        return datasource.postRegistration(body = body)
    }

    override suspend fun postLogout() {
        return datasource.postLogout()
    }

    override suspend fun postRefresh(): SignInResponse {
        return datasource.postRefresh()
    }
}
