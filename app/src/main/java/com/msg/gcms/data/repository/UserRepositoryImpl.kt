package com.msg.gcms.data.repository

import com.msg.gcms.data.mapper.UserMapper
import com.msg.gcms.data.remote.datasource.user.UserDataSource
import com.msg.gcms.data.remote.dto.user.modify_profile_image.ModifyProfileImageRequest
import com.msg.gcms.domain.data.user.get_my_profile.GetMyProfileData
import com.msg.gcms.domain.data.user.modify_profile_image.ModifyProfileImageData
import com.msg.gcms.domain.data.user.search_user.GetSearchUserData
import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource
) : UserRepository {
    override suspend fun getUserInfo(): GetMyProfileData {
        return UserMapper.mapperToGetMyProfileData(dataSource.getUserInfo())
    }

    override suspend fun putProfile(
        body: ModifyProfileImageData
    ) {
        return dataSource.putProfile(ModifyProfileImageRequest(url = body.url))
    }

    override suspend fun getUserSearch(QueryString: Map<String, String>): List<GetSearchUserData> {
        return dataSource.getUserSearch(QueryString)
            .map { UserMapper.mapperToGetSearchUserData(it) }
    }

    override suspend fun deleteUser() {
        return dataSource.deleteUser()
    }
}