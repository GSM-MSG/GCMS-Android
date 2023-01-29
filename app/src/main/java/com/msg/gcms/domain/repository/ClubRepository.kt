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
    ): Void

    suspend fun putChangeClub(
        body: ModifyClubInfoRequest
    ): Void

    suspend fun deleteClub(body: ClubIdentificationRequest): Void

    suspend fun getMemberList(clubName: String, type: String): MemberInfo

    suspend fun getApplicationList(clubName: String, type: String): MemberInfo

    suspend fun postApplicationAccept(body: MemberManagementRequest): Void

    suspend fun postApplicationReject(body: MemberManagementRequest): Void

    suspend fun putClubOpen(body: ClubIdentificationRequest): Void

    suspend fun putClubClose(body: ClubIdentificationRequest): Void

    suspend fun deleteMemberExpel(body: MemberManagementRequest): Void

    suspend fun postClubApply(body: ClubIdentificationRequest): Void

    suspend fun postClubCancel(body: ClubIdentificationRequest): Void

    suspend fun putDelegationOfRepresentation(body: MemberManagementRequest): Void
}