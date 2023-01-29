package com.msg.gcms.domain.datasource

import com.msg.gcms.data.remote.dto.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.user.response.UserData
import com.msg.gcms.data.remote.dto.user.response.UserInfoResponse

interface UserDataSource {
    suspend fun getUserInfo(): UserInfoResponse
    suspend fun putProfile(body: UserProfileRequest): Void
    suspend fun getUserSearch(QueryString: Map<String,String>): List<UserData>
    suspend fun postExit(body: UserDeleteRequest): Void
    suspend fun deleteUser(): Void
}