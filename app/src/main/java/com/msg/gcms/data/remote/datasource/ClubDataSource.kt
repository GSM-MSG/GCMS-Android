package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.club.response.MemberInfo
import com.msg.gcms.data.remote.dto.club.response.SummaryClubResponse

interface ClubDataSource {
    suspend fun getClubList(type: String): List<SummaryClubResponse>

    suspend fun getDetail(clubId: Long): ClubInfoResponse

    suspend fun postCreateClub(
        body: CreateClubRequest
    )

    suspend fun putChangeClub(
        body: ModifyClubInfoRequest,
        clubId: Long
    )

    suspend fun deleteClub(
        body: ClubIdentificationRequest
    )

    suspend fun getMemberList(clubName: String, type: String): MemberInfo

    suspend fun getApplicantList(clubName: String, type: String): MemberInfo

    suspend fun postApplicationAccept(body: MemberManagementRequest)

    suspend fun postApplicationReject(body: MemberManagementRequest)

    suspend fun putClubOpen(body: ClubIdentificationRequest)

    suspend fun putClubClose(body: ClubIdentificationRequest)

    suspend fun deleteMemberExpel(body: MemberManagementRequest)

    suspend fun postClubApply(body: ClubIdentificationRequest)

    suspend fun postClubCancel(body: ClubIdentificationRequest)

    suspend fun putDelegationOfRepresentation(body: MemberManagementRequest)
}