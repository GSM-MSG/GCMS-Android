package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.club.response.MemberInfo
import com.msg.gcms.data.remote.dto.club.response.SummaryClubResponse

interface ClubRepository {
    suspend fun getClubList(type: String): List<SummaryClubResponse>

    suspend fun getDetail(type: String, clubName: String): ClubInfoResponse

    suspend fun postCreateClub(
        body: CreateClubRequest
    )

    suspend fun putChangeClub(
        body: ModifyClubInfoRequest
    )

    suspend fun deleteClub(body: ClubIdentificationRequest)

    suspend fun getMemberList(clubName: String, type: String): MemberInfo

    suspend fun getApplicationList(clubName: String, type: String): MemberInfo

    suspend fun postApplicationAccept(body: MemberManagementRequest)

    suspend fun postApplicationReject(body: MemberManagementRequest)

    suspend fun putClubOpen(body: ClubIdentificationRequest)

    suspend fun putClubClose(body: ClubIdentificationRequest)

    suspend fun deleteMemberExpel(body: MemberManagementRequest)

    suspend fun postClubApply(body: ClubIdentificationRequest)

    suspend fun postClubCancel(body: ClubIdentificationRequest)

    suspend fun putDelegationOfRepresentation(body: MemberManagementRequest)
}