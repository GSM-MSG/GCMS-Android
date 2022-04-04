package com.msg.gcms.data.remote.network.common

import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.data.remote.dto.datasource.base.BaseResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CommonAPI {

    @POST("auth/register")
    fun postSignUp(
        @Body body : RegisterRequest
    ) : Single<BaseResponse>
}