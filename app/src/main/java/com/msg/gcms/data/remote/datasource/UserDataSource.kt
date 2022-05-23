package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import retrofit2.Response

interface UserDataSource {
    suspend fun getUserInfo(): Response<Void>
    suspend fun putProfile(body: UserProfileRequest): Response<Void>
    suspend fun getUserSearch(QueryString: UserSearchRequest): Response<Void>
    suspend fun deleteUser(body: UserDeleteRequest): Response<Void>
}