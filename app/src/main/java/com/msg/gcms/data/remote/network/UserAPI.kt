package com.msg.gcms.data.remote.network

import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.data.remote.dto.datasource.user.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.QueryMap

interface UserAPI {
    @GET("user/my")
    suspend fun getUserInfo(): UserInfoResponse

    @PUT("user/profile")
    suspend fun putProfile(
        @Body body: UserProfileRequest
    ): Void

    @GET("user/search")
    suspend fun getUserSearch(
        @QueryMap QueryString: Map<String, String>
    ): List<UserData>

    @POST("user/exit")
    suspend fun postExit(
        @Body body: UserDeleteRequest
    ): Void

    @DELETE("user/withdrawal")
    suspend fun deleteUser(): Void
}