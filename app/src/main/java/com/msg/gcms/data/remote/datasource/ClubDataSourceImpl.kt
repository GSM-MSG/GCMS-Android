package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.datasource.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.datasource.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.datasource.club.response.MemberInfo
import com.msg.gcms.data.remote.dto.datasource.club.response.SummaryClubResponse
import com.msg.gcms.data.remote.dto.datasource.club.response.UserInfo
import com.msg.gcms.data.remote.network.ClubAPI
import retrofit2.Response
import javax.inject.Inject

class ClubDataSourceImpl @Inject constructor(
    private val service : ClubAPI
) :  ClubDataSource {
    override suspend fun getClubList(type: String): Response<List<SummaryClubResponse>> {
        return service.getClubList(type = type)
    }

    override suspend fun getDetail(type: String, clubName: String): Response<ClubInfoResponse> {
        return service.getDetail(type = type, clubName = clubName)
    }

    override suspend fun deleteClub(): Response<Void> {
        return service.deleteClub()
    }

    override suspend fun getMemberList(clubName: String, type: String): Response<List<MemberInfo>> {
        return service.getMemberList(clubName = clubName, type= type)
    }

    override suspend fun getApplicantList(
        clubName: String,
        type: String
    ): Response<List<UserInfo>> {
        return service.getApplicantList(clubName = clubName, type = type)
    }

    override suspend fun postApplicationAccept(body: MemberManagementRequest): Response<Void> {
        return service.postApplicantAccept(body= body)
    }

    override suspend fun postApplicationReject(body: MemberManagementRequest): Response<Void> {
        return service.postApplicantReject(body = body)
    }

    override suspend fun putClubOpen(body: ClubIdentificationRequest): Response<Void> {
        return service.putClubOpen(body = body)
    }

    override suspend fun putClubClose(body: ClubIdentificationRequest): Response<Void> {
        return service.putClubClose(body = body)
    }

    override suspend fun deleteMemberExpel(body: MemberManagementRequest): Response<Void> {
        return service.deleteMemberExpel(body = body)
    }

    override suspend fun postClubApply(body: ClubIdentificationRequest): Response<Void> {
        return service.postClubApply(body = body)
    }
}