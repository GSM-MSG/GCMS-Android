package com.msg.gcms.data.remote.datasource.user

import com.msg.gcms.data.remote.dto.user.get_my_profile.GetMyProfileResponse
import com.msg.gcms.data.remote.dto.user.get_profile_image.GetProfileImageResponse
import com.msg.gcms.data.remote.dto.user.modify_profile_image.ModifyProfileImageRequest
import com.msg.gcms.data.remote.dto.user.search_user.GetSearchUserResponse

interface UserDataSource {
    suspend fun getUserInfo(): GetMyProfileResponse

    suspend fun putProfile(body: ModifyProfileImageRequest)

    suspend fun getProfileImage(): GetProfileImageResponse

    suspend fun getUserSearch(QueryString: Map<String,String>): List<GetSearchUserResponse>

    suspend fun deleteUser()
}