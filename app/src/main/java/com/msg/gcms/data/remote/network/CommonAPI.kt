package com.msg.gcms.data.remote.network

import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.QueryString
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.base.BaseResponse
import com.msg.gcms.di.module.NetworkModule
import dagger.Component
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface CommonAPI {

    @POST("auth/register")
    suspend fun postSignUp(
        @Body body : RegisterRequest
    ) : Response<Void>


    @POST("auth/verify")
    suspend fun postEmail(
         @Body body : CodeIssuanceRequest
    ) : Response<Void>

    @HEAD("auth/verify")
    suspend fun headCheckCode(
        @Query("email") email : String,
        @Query("code") code : String
    ) : Response<Void>
}