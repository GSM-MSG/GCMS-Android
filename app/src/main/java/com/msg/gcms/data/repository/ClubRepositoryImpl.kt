package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.ClubDataSourceImpl
import com.msg.gcms.data.remote.dto.datasource.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.datasource.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.datasource.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.datasource.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.datasource.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.datasource.club.response.MemberInfo
import com.msg.gcms.data.remote.dto.datasource.club.response.SummaryClubResponse
import com.msg.gcms.domain.repository.ClubRepository
import retrofit2.Response
import javax.inject.Inject

class ClubRepositoryImpl @Inject constructor(
    private val dataSource: ClubDataSourceImpl
) : ClubRepository {
    override suspend fun getClubList(type: String): Response<List<SummaryClubResponse>> {
        return dataSource.getClubList(type = type)
    }

    override suspend fun getDetail(type: String, clubName: String): Response<ClubInfoResponse> {
        return dataSource.getDetail(type = type, clubName = clubName)
    }

    override suspend fun postCreateClub(body: CreateClubRequest): Response<Void> {
        return dataSource.postCreateClub(body = body)
    }

    override suspend fun putChangeClub(body: ModifyClubInfoRequest): Response<Void> {
        return dataSource.putChangeClub(body = body)
    }


    override suspend fun deleteClub(): Response<Void> {
        return dataSource.deleteClub()
    }

    override suspend fun getMemberList(clubName: String, type: String): Response<List<MemberInfo>> {
        return dataSource.getMemberList(clubName = clubName, type = type)
    }

    override suspend fun getApplicationList(
        clubName: String,
        type: String
    ): Response<List<MemberInfo>> {
        return dataSource.getMemberList(clubName = clubName, type = type)
    }

    override suspend fun postApplicationAccept(body: MemberManagementRequest): Response<Void> {
        return dataSource.postApplicationAccept(body = body)
    }

    override suspend fun postApplicationReject(body: MemberManagementRequest): Response<Void> {
        return dataSource.postApplicationReject(body = body)
    }

    override suspend fun putClubOpen(body: ClubIdentificationRequest): Response<Void> {
        return dataSource.putClubOpen(body = body)
    }

    override suspend fun putClubClose(body: ClubIdentificationRequest): Response<Void> {
        return dataSource.putClubClose(body = body)
    }

    override suspend fun deleteMemberExpel(body: MemberManagementRequest): Response<Void> {
        return dataSource.deleteMemberExpel(body = body)
    }

    override suspend fun postClubApply(body: ClubIdentificationRequest): Response<Void> {
        return dataSource.postClubApply(body = body)
    }

    override suspend fun postClubCancel(body: ClubIdentificationRequest): Response<Void> {
        return dataSource.postClubCancel(body = body)
    }

    override suspend fun putDelegationOfRepresentation(body: MemberManagementRequest): Response<Void> {
        return dataSource.putDelegationOfRepresentation(body = body)
    }
}