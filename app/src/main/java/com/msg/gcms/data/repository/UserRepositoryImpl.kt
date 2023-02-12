package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.user_data.UserDataSourceImpl
import com.msg.gcms.data.remote.dto.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.user.response.UserData
import com.msg.gcms.data.remote.dto.user.response.UserInfoResponse
import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSourceImpl
) : UserRepository {
    override suspend fun getUserInfo(): UserInfoResponse {
        return dataSource.getUserInfo()
    }

    override suspend fun putProfile(
        body: UserProfileRequest
    ) {
        return dataSource.putProfile(body)
    }

    override suspend fun getUserSearch(QueryString: Map<String,String>): List<UserData> {
        return dataSource.getUserSearch(QueryString)
    }

    override suspend fun postExit(body: UserDeleteRequest) {
        return dataSource.postExit(body)
    }

    override suspend fun deleteUser() {
        return dataSource.deleteUser()
    }
}