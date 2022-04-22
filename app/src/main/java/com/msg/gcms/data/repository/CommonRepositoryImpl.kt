package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.CommonDataSourceImpl
import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.QueryString
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.base.BaseResponse
import com.msg.gcms.domain.repository.CommonRepository
import retrofit2.Response
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val datasource : CommonDataSourceImpl,
) : CommonRepository {
    override suspend fun postRegistration(request: RegisterRequest): BaseResponse {
        return datasource.postRegistration(body = request)
    }

    override suspend fun postEmail(body: CodeIssuanceRequest): Response<Void> {
        return datasource.postEmail(body)
    }

    override suspend fun headCheckCode(queryString: QueryString): BaseResponse {
        return headCheckCode(queryString)
    }
}