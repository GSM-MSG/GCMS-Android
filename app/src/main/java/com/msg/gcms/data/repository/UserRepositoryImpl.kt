package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.UserDataSourceImpl
import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserInfoResponse
import com.msg.gcms.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSourceImpl
) : UserRepository {
    override suspend fun getUserInfo(): Response<UserInfoResponse> {
        return dataSource.getUserInfo()
    }

    override suspend fun putProfile(
        body: UserProfileRequest
    ): Response<Void> {
        return dataSource.putProfile(body)
    }

    override suspend fun getUserSearch(QueryString: UserSearchRequest): Response<Void> {
        return dataSource.getUserSearch(QueryString)
    }

    override suspend fun deleteUser(
        body: UserDeleteRequest
    ): Response<Void> {
        return dataSource.deleteUser(body)
    }
}