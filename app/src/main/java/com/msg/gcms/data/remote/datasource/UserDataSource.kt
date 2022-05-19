package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import retrofit2.Response

interface UserDataSource {
    suspend fun getUserInfo(Authorization: String): Response<Void>
    suspend fun putProfile(Authorization: String, body: UserProfileRequest): Response<Void>
    suspend fun getUserSearch(QueryString: UserSearchRequest): Response<Void>
    suspend fun deleteUser(Authorization: String, body: UserDeleteRequest): Response<Void>
}