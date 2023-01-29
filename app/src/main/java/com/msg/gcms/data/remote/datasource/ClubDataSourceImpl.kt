package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.club.response.MemberInfo
import com.msg.gcms.data.remote.dto.club.response.SummaryClubResponse
import com.msg.gcms.data.remote.network.ClubAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import com.msg.gcms.domain.datasource.ClubDataSource
import javax.inject.Inject

class ClubDataSourceImpl @Inject constructor(
    private val service: ClubAPI
) : ClubDataSource {

    override suspend fun getClubList(type: String): List<SummaryClubResponse> {
        return GCMSApiHandler<List<SummaryClubResponse>>()
            .httpRequest { service.getClubList(type = type) }
            .sendRequest()
    }

    override suspend fun getDetail(type: String, clubName: String): ClubInfoResponse {
        return GCMSApiHandler<ClubInfoResponse>()
            .httpRequest { service.getDetail(type = type, clubName = clubName) }
            .sendRequest()
    }

    override suspend fun postCreateClub(body: CreateClubRequest): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.postCreateClub(body = body) }
            .sendRequest()
    }

    override suspend fun putChangeClub(body: ModifyClubInfoRequest): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.putChangeClub(body = body)}
            .sendRequest()
    }

    override suspend fun deleteClub(body: ClubIdentificationRequest): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.deleteClub(body = body) }
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

    override suspend fun postApplicationAccept(body: MemberManagementRequest): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.postApplicantAccept(body = body) }
            .sendRequest()
    }

    override suspend fun postApplicationReject(body: MemberManagementRequest): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.postApplicantReject(body = body) }
            .sendRequest()
    }

    override suspend fun putClubOpen(body: ClubIdentificationRequest): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.putClubOpen(body = body) }
            .sendRequest()
    }

    override suspend fun putClubClose(body: ClubIdentificationRequest): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.putClubClose(body = body) }
            .sendRequest()

    }

    override suspend fun deleteMemberExpel(body: MemberManagementRequest): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.deleteMemberExpel(body = body) }
            .sendRequest()
    }

    override suspend fun postClubApply(body: ClubIdentificationRequest): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.postClubApply(body = body) }
            .sendRequest()
    }

    override suspend fun postClubCancel(body: ClubIdentificationRequest): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.postClubCancel(body = body) }
            .sendRequest()
    }

    override suspend fun putDelegationOfRepresentation(body: MemberManagementRequest): Void {
        return GCMSApiHandler<Void>()
            .httpRequest { service.putDelegationOfRepresentation(body = body) }
            .sendRequest()
    }
}