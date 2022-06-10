package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.data.remote.dto.datasource.user.response.UserInfoResponse
import com.msg.gcms.data.remote.network.UserAPI
import retrofit2.Response
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val service: UserAPI
) : UserDataSource {
    override suspend fun getUserInfo(): Response<UserInfoResponse> {
        return service.getUserInfo()
    }

    override suspend fun putProfile(
        body: UserProfileRequest
    ): Response<Void> {
        return service.putProfile(body)
    }

    override suspend fun getUserSearch(QueryString: UserSearchRequest): Response<UserData> {
        return service.getUserSearch(QueryString)
    }

    override suspend fun deleteUser(
        body: UserDeleteRequest
    ): Response<Void> {
        return service.deleteUser(body)
    }
}