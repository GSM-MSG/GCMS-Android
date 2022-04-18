package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.QueryString
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.base.BaseResponse

interface CommonRepository {
    suspend fun postRegistration(
        request : RegisterRequest
    ) : BaseResponse

    suspend fun postEmail(
        body : CodeIssuanceRequest
    ) : BaseResponse

    suspend fun headCheckCode (
        queryString: QueryString
    ) : BaseResponse
}