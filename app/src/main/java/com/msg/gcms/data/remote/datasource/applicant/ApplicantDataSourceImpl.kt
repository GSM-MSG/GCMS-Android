package com.msg.gcms.data.remote.datasource.applicant

import com.msg.gcms.data.remote.dto.applicant.get_applicant_list.GetApplicantListResponse
import com.msg.gcms.data.remote.network.api.ApplicantAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import com.msg.gcms.data.remote.dto.applicant.club_apply_accept.ClubApplyAcceptRequest
import com.msg.gcms.data.remote.dto.applicant.club_apply_reject.ClubApplyRejectRequest
import javax.inject.Inject

class ApplicantDataSourceImpl @Inject constructor(
    val service: ApplicantAPI
) : ApplicantDataSource {
    override suspend fun getApplicantList(clubId: Long): GetApplicantListResponse {
        return GCMSApiHandler<GetApplicantListResponse>()
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

    override suspend fun postApplicantAccept(clubId: Long ,body: ClubApplyAcceptRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.postApplicantAccept(clubId = clubId, body = body) }
            .sendRequest()
    }

    override suspend fun postApplicantReject(clubId: Long, body: ClubApplyRejectRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.postApplicantReject(clubId = clubId, body = body) }
            .sendRequest()
    }
}