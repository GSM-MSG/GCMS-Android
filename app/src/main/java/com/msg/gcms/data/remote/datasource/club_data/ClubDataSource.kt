package com.msg.gcms.data.remote.datasource.club_data

import com.msg.gcms.data.remote.dto.club.modify_club_info.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.club.get_club_detail.ClubDetailResponse
import com.msg.gcms.data.remote.dto.club.response.SummaryClubResponse

interface ClubDataSource {
    suspend fun getClubList(type: String): List<SummaryClubResponse>

    suspend fun getDetail(clubId: Long): ClubDetailResponse

    suspend fun postCreateClub(
        body: _root_ide_package_.com.msg.gcms.data.remote.dto.club.create_club.CreateClubRequest
    )

    suspend fun putChangeClub(
        body: ModifyClubInfoRequest,
        clubId: Long
    )

    suspend fun exitClub(
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