package com.msg.gcms.data.remote.datasource.club_member

import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club_member.get_club_member.GetClubMemberResponse
import com.msg.gcms.data.remote.network.api.ClubMemberAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import javax.inject.Inject

class ClubMemberDataSourceImpl @Inject constructor(
    private val service: ClubMemberAPI
) : ClubMemberDataSource {
    override suspend fun getMemberList(clubId: Long): GetClubMemberResponse {
        return GCMSApiHandler<GetClubMemberResponse>()
            .httpRequest { service.getMemberList(clubId = clubId) }
            .sendRequest()
    }

    override suspend fun deleteMemberExpel(
        clubId: Long,
        body: MemberManagementRequest
    ) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.deleteMemberExpel(clubId = clubId, body = body) }
            .sendRequest()
    }

    override suspend fun putDelegationOfRepresentation(
        clubId: Long,
        body: MemberManagementRequest
    ) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.putDelegationOfRepresentation(clubId = clubId, body = body) }
            .sendRequest()
    }
}