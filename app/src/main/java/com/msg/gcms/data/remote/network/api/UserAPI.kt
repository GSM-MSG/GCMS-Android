package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.user.response.UserData
import com.msg.gcms.data.remote.dto.user.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.QueryMap

interface UserAPI {
    @GET("user/")
    suspend fun getUserInfo(): UserInfoResponse

    @PATCH("user/")
    suspend fun putProfile(
        @Body body: UserProfileRequest
    ): Void

    @GET("user/search")
    suspend fun getUserSearch(
        @QueryMap QueryString: Map<String, String>
    ): List<UserData>

    @DELETE("user/withdrawal")
    suspend fun deleteUser(): Void
}