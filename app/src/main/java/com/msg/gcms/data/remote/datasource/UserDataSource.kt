package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.data.remote.dto.datasource.user.response.UserInfoResponse
import retrofit2.Response

interface UserDataSource {
    suspend fun getUserInfo(): Response<UserInfoResponse>
    suspend fun putProfile(body: UserProfileRequest): Response<Void>
    suspend fun getUserSearch(QueryString: UserSearchRequest): Response<UserData>
    suspend fun deleteUser(body: UserDeleteRequest): Response<Void>
}