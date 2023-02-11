package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.club.response.MemberInfo
import com.msg.gcms.data.remote.dto.club.response.SummaryClubResponse
import com.msg.gcms.data.remote.network.api.ClubAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import javax.inject.Inject

class ClubDataSourceImpl @Inject constructor(
    private val service: ClubAPI
) : ClubDataSource {

    override suspend fun getClubList(type: String): List<SummaryClubResponse> {
        return GCMSApiHandler<List<SummaryClubResponse>>()
            .httpRequest { service.getClubList(type = type) }
            .sendRequest()
    }

    override suspend fun getDetail(clubId: Long): ClubInfoResponse {
        return GCMSApiHandler<ClubInfoResponse>()
            .httpRequest { service.getDetail(clubId) }
            .sendRequest()
    }

    override suspend fun postCreateClub(body: CreateClubRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.postCreateClub(body = body) }
            .sendRequest()
    }

    override suspend fun putChangeClub(body: ModifyClubInfoRequest, clubId: Long) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.putChangeClub(body = body, clubId = clubId)}
            .sendRequest()
    }

    override suspend fun deleteClub(clubId: Long) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.deleteClub(clubId = clubId) }
            .sendRequest()
    }

    override suspend fun getMemberList(clubName: String, type: String): MemberInfo {
        return GCMSApiHandler<MemberInfo>()
            .httpRequest { service.getMemberList(clubName = clubName, type = type) }
            .sendRequest()
    }

    override suspend fun getApplicantList(
        clubName: String,
        type: String
    ): MemberInfo {
        return GCMSApiHandler<MemberInfo>()
            .httpRequest { service.getApplicantList(clubName = clubName, type = type) }
            .sendRequest()
    }

    override suspend fun postApplicationAccept(body: MemberManagementRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.postApplicantAccept(body = body) }
            .sendRequest()
    }

    override suspend fun postApplicationReject(body: MemberManagementRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.postApplicantReject(body = body) }
            .sendRequest()
    }

    override suspend fun putClubOpen(body: ClubIdentificationRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.putClubOpen(body = body) }
            .sendRequest()
    }

    override suspend fun putClubClose(body: ClubIdentificationRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.putClubClose(body = body) }
            .sendRequest()

    }

    override suspend fun deleteMemberExpel(body: MemberManagementRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.deleteMemberExpel(body = body) }
            .sendRequest()
    }

    override suspend fun postClubApply(body: ClubIdentificationRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.postClubApply(body = body) }
            .sendRequest()
    }

    override suspend fun postClubCancel(body: ClubIdentificationRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.postClubCancel(body = body) }
            .sendRequest()
    }

    override suspend fun putDelegationOfRepresentation(body: MemberManagementRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.putDelegationOfRepresentation(body = body) }
            .sendRequest()
    }
}