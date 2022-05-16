package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.UserDataSourceImpl
import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import com.msg.gcms.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSourceImpl
) : UserRepository {
    override suspend fun getUserInfo(Authorization: String): Response<Void> {
        return dataSource.getUserInfo(Authorization)
    }

    override suspend fun putProfile(
        Authorization: String,
        body: UserProfileRequest
    ): Response<Void> {
        return dataSource.putProfile(Authorization, body)
    }

    override suspend fun getUserSearch(QueryString: UserSearchRequest): Response<Void> {
        return dataSource.getUserSearch(QueryString)
    }

    override suspend fun deleteUser(
        Authorization: String,
        body: UserDeleteRequest
    ): Response<Void> {
        return dataSource.deleteUser(Authorization, body)
    }
}