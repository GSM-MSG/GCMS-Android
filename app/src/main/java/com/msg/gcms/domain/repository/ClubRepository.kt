package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.club.response.SummaryClubResponse

interface ClubRepository {
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

    suspend fun putClubOpen(
        clubId: Long
    )

    suspend fun putClubClose(
        clubId: Long
    )
}