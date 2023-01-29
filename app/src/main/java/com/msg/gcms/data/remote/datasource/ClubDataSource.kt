package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.dto.datasource.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.datasource.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.datasource.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.datasource.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.datasource.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.datasource.club.response.MemberInfo
import com.msg.gcms.data.remote.dto.datasource.club.response.SummaryClubResponse

interface ClubDataSource {
    suspend fun getClubList(type: String): List<SummaryClubResponse>

    suspend fun getDetail(type: String, clubName: String): ClubInfoResponse

    suspend fun postCreateClub(
        body: CreateClubRequest
    ): Void

    suspend fun putChangeClub(
        body: ModifyClubInfoRequest
    ): Void

    suspend fun deleteClub(
        body: ClubIdentificationRequest
    ): Void

    suspend fun getMemberList(clubName: String, type: String): MemberInfo

    suspend fun getApplicantList(clubName: String, type: String): MemberInfo

    suspend fun postApplicationAccept(body: MemberManagementRequest): Void

    suspend fun postApplicationReject(body: MemberManagementRequest): Void

    suspend fun putClubOpen(body: ClubIdentificationRequest): Void

    suspend fun putClubClose(body: ClubIdentificationRequest): Void

    suspend fun deleteMemberExpel(body: MemberManagementRequest): Void

    suspend fun postClubApply(body: ClubIdentificationRequest): Void

    suspend fun postClubCancel(body: ClubIdentificationRequest): Void

    suspend fun putDelegationOfRepresentation(body: MemberManagementRequest): Void
}