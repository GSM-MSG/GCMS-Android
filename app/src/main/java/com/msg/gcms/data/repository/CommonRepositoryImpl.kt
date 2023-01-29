package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.CommonDataSourceImpl
import com.msg.gcms.data.remote.dto.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.auth.response.RegisterResponse
import com.msg.gcms.domain.repository.CommonRepository
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val datasource: CommonDataSourceImpl,
) : CommonRepository {
    override suspend fun postRegistration(body: RegisterRequest): RegisterResponse {
        return datasource.postRegistration(body = body)
    }

    override suspend fun postLogout(): Void {
        return datasource.postLogout()
    }

    override suspend fun postRefresh(): RegisterResponse {
        return datasource.postRefresh()
    }
}
