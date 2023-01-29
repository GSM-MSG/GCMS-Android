package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.data.remote.dto.datasource.user.response.UserInfoResponse

interface UserDataSource {
    suspend fun getUserInfo(): UserInfoResponse
    suspend fun putProfile(body: UserProfileRequest): Void
    suspend fun getUserSearch(QueryString: Map<String,String>): List<UserData>
    suspend fun postExit(body: UserDeleteRequest): Void
    suspend fun deleteUser(): Void
}