package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.QueryString
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.base.BaseResponse
import retrofit2.Response

interface CommonDataSource {
    suspend fun postRegistration(body : RegisterRequest) : BaseResponse

    suspend fun postEmail(body : CodeIssuanceRequest) : Response<Void>

    suspend fun headCheckCode (queryString: QueryString) : BaseResponse
}