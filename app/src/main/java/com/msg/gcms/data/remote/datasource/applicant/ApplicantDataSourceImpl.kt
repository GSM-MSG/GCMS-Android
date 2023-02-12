package com.msg.gcms.data.remote.datasource.applicant

import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.response.MemberInfo
import com.msg.gcms.data.remote.network.api.ApplicantAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import javax.inject.Inject

class ApplicantDataSourceImpl @Inject constructor(
    val service: ApplicantAPI
) : ApplicantDataSource {
    override suspend fun getApplicantList(clubId: Long): MemberInfo {
        return GCMSApiHandler<MemberInfo>()
            .httpRequest { service.getApplicantList(clubId) }
            .sendRequest()
    }

    override suspend fun postClubApply(clubId: Long) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.postClubApply(clubId) }
            .sendRequest()
    }

    override suspend fun deleteClubApply(clubId: Long) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.deleteClubApply(clubId) }
            .sendRequest()
    }

    override suspend fun postApplicantAccept(clubId: Long ,body: MemberManagementRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.postApplicantAccept(clubId = clubId, body = body) }
            .sendRequest()
    }

    override suspend fun postApplicantReject(clubId: Long, body: MemberManagementRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.postApplicantReject(clubId = clubId, body = body) }
            .sendRequest()
    }
}