package com.msg.gcms.data.remote.network

import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserAPI {
    @GET("user/my")
    suspend fun getUserInfo(): Response<UserInfoResponse>

    @PUT("user/profile")
    suspend fun putProfile(
        body: UserProfileRequest
    ) : Response<Void>

    @GET("user/search")
    suspend fun getUserSearch(
        QueryString:UserSearchRequest
    ) : Response<Void>

    @DELETE("user/exit")
    suspend fun deleteUser(
        body: UserDeleteRequest
    ) : Response<Void>
}