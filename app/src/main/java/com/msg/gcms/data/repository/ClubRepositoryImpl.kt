package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.ClubDataSourceImpl
import com.msg.gcms.data.remote.dto.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.club.response.MemberInfo
import com.msg.gcms.data.remote.dto.club.response.SummaryClubResponse
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ClubRepositoryImpl @Inject constructor(
    private val dataSource: ClubDataSourceImpl
) : ClubRepository {
    override suspend fun getClubList(type: String): List<SummaryClubResponse> {
        return dataSource.getClubList(type = type)
    }

    override suspend fun getDetail(clubId: Long): ClubInfoResponse {
        return dataSource.getDetail(clubId)
    }

    override suspend fun postCreateClub(body: CreateClubRequest) {
        return dataSource.postCreateClub(body = body)
    }

    override suspend fun putChangeClub(body: ModifyClubInfoRequest, clubId: Long) {
        return dataSource.putChangeClub(body = body, clubId = clubId)
    }

    override suspend fun deleteClub(clubId: Long) {
        return dataSource.deleteClub(clubId = clubId)
    }

    override suspend fun getMemberList(clubName: String, type: String): MemberInfo {
        return dataSource.getMemberList(clubName = clubName, type = type)
    }

    override suspend fun getApplicationList(
        clubName: String,
        type: String
    ): MemberInfo {
        return dataSource.getApplicantList(clubName = clubName, type = type)
    }

    override suspend fun postApplicationAccept(body: MemberManagementRequest) {
        return dataSource.postApplicationAccept(body = body)
    }

    override suspend fun postApplicationReject(body: MemberManagementRequest) {
        return dataSource.postApplicationReject(body = body)
    }

    override suspend fun putClubOpen(body: ClubIdentificationRequest) {
        return dataSource.putClubOpen(body = body)
    }

    override suspend fun putClubClose(body: ClubIdentificationRequest) {
        return dataSource.putClubClose(body = body)
    }

    override suspend fun deleteMemberExpel(body: MemberManagementRequest) {
        return dataSource.deleteMemberExpel(body = body)
    }

    override suspend fun postClubApply(body: ClubIdentificationRequest) {
        return dataSource.postClubApply(body = body)
    }

    override suspend fun postClubCancel(body: ClubIdentificationRequest) {
        return dataSource.postClubCancel(body = body)
    }

    override suspend fun putDelegationOfRepresentation(body: MemberManagementRequest) {
        return dataSource.putDelegationOfRepresentation(body = body)
    }
}