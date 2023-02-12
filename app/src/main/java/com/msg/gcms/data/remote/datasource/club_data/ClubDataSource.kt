package com.msg.gcms.data.remote.datasource.club_data

import com.msg.gcms.data.remote.dto.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.club.response.ClubInfoResponse
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
        clubId: Long
    )

    suspend fun putClubOpen(body: ClubIdentificationRequest)

    suspend fun putClubClose(body: ClubIdentificationRequest)

}