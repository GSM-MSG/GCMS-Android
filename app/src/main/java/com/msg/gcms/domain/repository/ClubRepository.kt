package com.msg.gcms.domain.repository

import Macaroni
import com.msg.gcms.domain.data.club.create_club.CreateClubData
import com.msg.gcms.domain.data.club.get_club_detail.ClubDetailData
import com.msg.gcms.domain.data.club.get_club_list.GetClubListData
import com.msg.gcms.domain.data.club.modify_club_info.ModifyClubInfoData

interface ClubRepository {
    suspend fun getClubList(type: String): Macaroni<List<GetClubListData>>

    suspend fun getDetail(clubId: Long): ClubDetailData

    suspend fun postCreateClub(
        body: CreateClubData
    )

    suspend fun putChangeClub(
        body: ModifyClubInfoData,
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