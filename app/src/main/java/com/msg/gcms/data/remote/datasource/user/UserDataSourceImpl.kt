package com.msg.gcms.data.remote.datasource.user

import com.msg.gcms.data.remote.dto.user.get_my_profile.GetMyProfileResponse
import com.msg.gcms.data.remote.dto.user.modify_profile_image.ModifyProfileImageRequest
import com.msg.gcms.data.remote.dto.user.response.UserData
import com.msg.gcms.data.remote.network.api.UserAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val service: UserAPI
) : UserDataSource {
    override suspend fun getUserInfo(): GetMyProfileResponse {
        return GCMSApiHandler<GetMyProfileResponse>()
            .httpRequest { service.getUserInfo() }
            .sendRequest()
    }

    override suspend fun putProfile(
        body: ModifyProfileImageRequest
    ) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.putProfile(body) }
            .sendRequest()
    }

    override suspend fun getUserSearch(QueryString: Map<String, String>): List<UserData> {
        return GCMSApiHandler<List<UserData>>()
            .httpRequest { service.getUserSearch(QueryString) }
            .sendRequest()
    }

    override suspend fun deleteUser() {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.deleteUser() }
            .sendRequest()
    }
}