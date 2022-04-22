package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.QueryString
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.base.BaseResponse
import com.msg.gcms.data.remote.network.CommonAPI
import retrofit2.Response
import javax.inject.Inject

class CommonDataSourceImpl @Inject constructor(
    private val service: CommonAPI
) : CommonDataSource {
    override suspend fun postRegistration(body: RegisterRequest): BaseResponse {
        return service.postSignUp(body)
    }

    override suspend fun postEmail(body: CodeIssuanceRequest): Response<Void> {
        return service.postEmail(body)
    }

    override suspend fun headCheckCode(queryString: QueryString): BaseResponse {
        return service.headCheckCode(queryString)
    }

}