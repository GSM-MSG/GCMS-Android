package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.user.get_my_profile.GetMyProfileResponse
import com.msg.gcms.data.remote.dto.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.user.response.UserData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.QueryMap

interface UserAPI {
    @GET("user/")
    suspend fun getUserInfo(): GetMyProfileResponse

    @GET("user/search")
    suspend fun getUserSearch(
        @QueryMap QueryString: Map<String, String>
    ): List<UserData>

    @PATCH("user/")
    suspend fun putProfile(
        @Body body: UserProfileRequest
    ): Void

    @DELETE("user/")
    suspend fun deleteUser(): Void
}