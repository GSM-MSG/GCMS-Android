package com.msg.gcms.data.remote.network

import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserAPI {
    @GET("user/my")
    suspend fun getUserInfo(
        Authorization: String
    ): Response<Void>

    @PUT("user/profile")
    suspend fun putProfile(
        Authorization: String,
        body: UserProfileRequest
    ) : Response<Void>

    @GET("user/search")
    suspend fun getUserSearch(
        QueryString:UserSearchRequest
    ) : Response<Void>

    @DELETE("user/exit")
    suspend fun deleteUser(
        Authorization: String,
        body: UserDeleteRequest
    ) : Response<Void>
}