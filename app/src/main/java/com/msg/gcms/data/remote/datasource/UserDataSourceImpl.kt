package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.data.remote.dto.datasource.user.response.UserInfoResponse
import com.msg.gcms.data.remote.network.UserAPI
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val service: UserAPI
) : UserDataSource {
    override suspend fun getUserInfo(): UserInfoResponse {
        return service.getUserInfo()
    }

    override suspend fun putProfile(
        body: UserProfileRequest
    ): Void {
        return service.putProfile(body)
    }

    override suspend fun getUserSearch(QueryString: Map<String,String>): List<UserData> {
        return service.getUserSearch(QueryString)
    }

    override suspend fun postExit(body: UserDeleteRequest): Void {
        return service.postExit(body)
    }

    override suspend fun deleteUser(): Void {
        return service.deleteUser()
    }
}