package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.CommonDataSourceImpl
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.auth.response.RegisterResponse
import com.msg.gcms.domain.repository.CommonRepository
import retrofit2.Response
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val datasource: CommonDataSourceImpl,
) : CommonRepository {
    override suspend fun postRegistration(body: RegisterRequest): Response<RegisterResponse> {
        return datasource.postRegistration(body = body)
    }

    override suspend fun postLogout(): Response<Void> {
        return datasource.postLogout()
    }

    override suspend fun postRefresh(): Response<RegisterResponse> {
        return datasource.postRefresh()
    }
}
