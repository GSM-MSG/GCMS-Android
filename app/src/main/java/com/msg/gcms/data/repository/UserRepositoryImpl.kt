package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.user.UserDataSource
import com.msg.gcms.data.remote.dto.user.get_my_profile.toGetMyProfileData
import com.msg.gcms.data.remote.dto.user.get_profile_image.toGetProfileImageData
import com.msg.gcms.data.remote.dto.user.modify_profile_image.ModifyProfileImageRequest
import com.msg.gcms.data.remote.dto.user.search_user.toGetSearchUserData
import com.msg.gcms.domain.data.user.get_my_profile.GetMyProfileData
import com.msg.gcms.domain.data.user.get_profile_image.GetProfileImageData
import com.msg.gcms.domain.data.user.modify_profile_image.ModifyProfileImageData
import com.msg.gcms.domain.data.user.search_user.GetSearchUserData
import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource
) : UserRepository {
    override suspend fun getUserInfo(): GetMyProfileData {
        return dataSource.getUserInfo().toGetMyProfileData()
    }

    override suspend fun putProfile(
        body: ModifyProfileImageData
    ) {
        return dataSource.putProfile(ModifyProfileImageRequest(url = body.url))
    }

    override suspend fun getUserSearch(QueryString: Map<String, String>): List<GetSearchUserData> {
        return dataSource.getUserSearch(QueryString)
            .map { it.toGetSearchUserData() }
    }

    override suspend fun getProfileImage(): GetProfileImageData {
        return dataSource.getProfileImage().toGetProfileImageData()
    }

    override suspend fun deleteUser() {
        return dataSource.deleteUser()
    }
}