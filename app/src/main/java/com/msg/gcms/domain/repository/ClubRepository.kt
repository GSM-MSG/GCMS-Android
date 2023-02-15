package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.club.create_club.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.get_club_detail.ClubDetailResponse
import com.msg.gcms.data.remote.dto.club.get_club_list.GetClubListResponse
import com.msg.gcms.data.remote.dto.club.modify_club_info.ModifyClubInfoRequest

interface ClubRepository {
    suspend fun getClubList(type: String): List<GetClubListResponse>

    suspend fun getDetail(clubId: Long): ClubDetailResponse

    suspend fun postCreateClub(
        body: CreateClubRequest
    )

    suspend fun putChangeClub(
        body: ModifyClubInfoRequest,
        clubId: Long
    )

    suspend fun putClubOpen(
        clubId: Long
    )

    suspend fun putClubClose(
        clubId: Long
    )

    suspend fun exitClub(
        clubId: Long
    )

    suspend fun deleteClub(
        clubId: Long
    )
}