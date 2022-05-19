package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import com.msg.gcms.data.remote.network.UserAPI
import retrofit2.Response
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val service: UserAPI
) : UserDataSource {
    override suspend fun getUserInfo(Authorization: String): Response<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun putProfile(
        Authorization: String,
        body: UserProfileRequest
    ): Response<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserSearch(QueryString: UserSearchRequest): Response<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(
        Authorization: String,
        body: UserDeleteRequest
    ): Response<Void> {
        TODO("Not yet implemented")
    }
}