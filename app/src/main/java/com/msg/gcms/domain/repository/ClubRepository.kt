package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.datasource.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.datasource.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.datasource.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.datasource.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.datasource.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.datasource.club.response.MemberInfo
import com.msg.gcms.data.remote.dto.datasource.club.response.SummaryClubResponse
import retrofit2.Response

interface ClubRepository {
    suspend fun getClubList(type: String): Response<List<SummaryClubResponse>>

    suspend fun getDetail(type: String, clubName: String): Response<ClubInfoResponse>

    suspend fun postCreateClub(
        body: CreateClubRequest
    ): Response<Void>

    suspend fun putChangeClub(
        body: ModifyClubInfoRequest
    ): Response<Void>

    suspend fun deleteClub(body: ClubIdentificationRequest): Response<Void>

    suspend fun getMemberList(clubName: String, type: String): Response<MemberInfo>

    suspend fun getApplicationList(clubName: String, type: String): Response<MemberInfo>

    suspend fun postApplicationAccept(body: MemberManagementRequest): Response<Void>

    suspend fun postApplicationReject(body: MemberManagementRequest): Response<Void>

    suspend fun putClubOpen(body: ClubIdentificationRequest): Response<Void>

    suspend fun putClubClose(body: ClubIdentificationRequest): Response<Void>

    suspend fun deleteMemberExpel(body: MemberManagementRequest): Response<Void>

    suspend fun postClubApply(body: ClubIdentificationRequest): Response<Void>

    suspend fun postClubCancel(body: ClubIdentificationRequest): Response<Void>

    suspend fun putDelegationOfRepresentation(body: MemberManagementRequest): Response<Void>
}