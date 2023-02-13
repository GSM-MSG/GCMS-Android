package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.user.response.UserData
import com.msg.gcms.data.remote.dto.user.response.UserInfoResponse

interface UserRepository {
    suspend fun getUserInfo(): UserInfoResponse

    suspend fun putProfile(body: UserProfileRequest)

    suspend fun getUserSearch(QueryString: Map<String,String>): List<UserData>

    suspend fun deleteUser()
}