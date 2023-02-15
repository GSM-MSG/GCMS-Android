package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.club_data.ClubDataSourceImpl
import com.msg.gcms.data.remote.dto.club.create_club.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.get_club_detail.ClubDetailResponse
import com.msg.gcms.data.remote.dto.club.get_club_list.GetClubListResponse
import com.msg.gcms.data.remote.dto.club.modify_club_info.ModifyClubInfoRequest
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ClubRepositoryImpl @Inject constructor(
    private val dataSource: ClubDataSourceImpl
) : ClubRepository {
    override suspend fun getClubList(type: String): List<GetClubListResponse> {
        return dataSource.getClubList(type = type)
    }

    override suspend fun getDetail(clubId: Long): ClubDetailResponse {
        return dataSource.getDetail(clubId)
    }

    override suspend fun postCreateClub(body: CreateClubRequest) {
        return dataSource.postCreateClub(body = body)
    }

    override suspend fun putChangeClub(body: ModifyClubInfoRequest, clubId: Long) {
        return dataSource.putChangeClub(body = body, clubId = clubId)
    }

    override suspend fun deleteClub(clubId: Long) {
        return dataSource.deleteClub(clubId = clubId)
    }

    override suspend fun putClubOpen(clubId: Long) {
        return dataSource.putClubOpen(clubId = clubId)
    }

    override suspend fun putClubClose(clubId: Long) {
        return dataSource.putClubClose(clubId = clubId)
    }

    override suspend fun exitClub(clubId: Long) {
        return dataSource.deleteClub(clubId = clubId)
    }
}