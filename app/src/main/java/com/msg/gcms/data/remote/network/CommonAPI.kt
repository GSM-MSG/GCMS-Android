package com.msg.gcms.data.remote.network

import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.QueryString
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.base.BaseResponse
import com.msg.gcms.di.module.NetworkModule
import dagger.Component
import retrofit2.http.*

interface CommonAPI {

    @POST("auth/register")
    suspend fun postSignUp(
        @Body body : RegisterRequest
    ) : BaseResponse


    @POST("auth/verify")
    suspend fun postEmail(
         @Body body : CodeIssuanceRequest
    ) : BaseResponse

    @HEAD("auth/verify")
    suspend fun headCheckCode(
        @Field("QueryString") QueryString : QueryString
    ) : BaseResponse
}