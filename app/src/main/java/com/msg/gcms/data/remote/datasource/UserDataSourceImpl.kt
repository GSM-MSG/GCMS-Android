package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.data.remote.dto.datasource.user.response.UserInfoResponse
import com.msg.gcms.data.remote.network.UserAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import com.msg.gcms.domain.datasource.UserDataSource
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val service: UserAPI
) : UserDataSource {
    override suspend fun getUserInfo(): UserInfoResponse {
        return GCMSApiHandler<UserInfoResponse>()
            .httpRequest { service.getUserInfo() }
            .sendRequest()
    }

    override suspend fun putProfile(
        body: UserProfileRequest
    ): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.putProfile(body) }
            .sendRequest()
    }

    override suspend fun getUserSearch(QueryString: Map<String, String>): List<UserData> {
        return GCMSApiHandler<List<UserData>>()
            .httpRequest { service.getUserSearch(QueryString) }
            .sendRequest()
    }

    override suspend fun postExit(body: UserDeleteRequest): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.postExit(body) }
            .sendRequest()
    }

    override suspend fun deleteUser(): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.deleteUser() }
            .sendRequest()
    }
}