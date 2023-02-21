package com.msg.gcms.domain.repository

import com.msg.gcms.domain.data.user.get_my_profile.GetMyProfileData
import com.msg.gcms.domain.data.user.modify_profile_image.ModifyProfileImageData
import com.msg.gcms.domain.data.user.search_user.GetSearchUserData

interface UserRepository {
    suspend fun getUserInfo(): GetMyProfileData

    suspend fun putProfile(body: ModifyProfileImageData)

    suspend fun getUserSearch(QueryString: Map<String,String>): List<GetSearchUserData>

    suspend fun deleteUser()
}