package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.user.get_my_profile.GetMyProfileResponse
import com.msg.gcms.data.remote.dto.user.modify_profile_image.ModifyProfileImageRequest
import com.msg.gcms.data.remote.dto.user.search_user.GetSearchUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.QueryMap

interface UserAPI {
    @GET("user")
    suspend fun getUserInfo(): GetMyProfileResponse

    @GET("user/search")
    suspend fun getUserSearch(
        @QueryMap QueryString: Map<String, String>
    ): List<GetSearchUserResponse>

    @PATCH("user")
    suspend fun putProfile(
        @Body body: ModifyProfileImageRequest
    ): Response<Unit>

    @DELETE("user")
    suspend fun deleteUser(): Response<Unit>
}