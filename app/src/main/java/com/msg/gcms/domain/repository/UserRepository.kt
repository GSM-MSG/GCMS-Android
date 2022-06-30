package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.data.remote.dto.datasource.user.response.UserInfoResponse
import retrofit2.Response

interface UserRepository {
    suspend fun getUserInfo(): Response<UserInfoResponse>
    suspend fun putProfile(body: UserProfileRequest): Response<Void>
    suspend fun getUserSearch(QueryString: Map<String,String>): Response<List<UserData>>
    suspend fun postExit(body: UserDeleteRequest): Response<Void>
    suspend fun deleteUser(): Response<Void>
}