package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.club_data.ClubDataSourceImpl
import com.msg.gcms.data.remote.dto.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.club.response.SummaryClubResponse
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ClubRepositoryImpl @Inject constructor(
    private val dataSource: ClubDataSourceImpl
) : ClubRepository {
    override suspend fun getClubList(type: String): List<SummaryClubResponse> {
        return dataSource.getClubList(type = type)
    }

    override suspend fun getDetail(clubId: Long): ClubInfoResponse {
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

    override suspend fun putClubOpen(body: ClubIdentificationRequest) {
        return dataSource.putClubOpen(body = body)
    }

    override suspend fun putClubClose(body: ClubIdentificationRequest) {
        return dataSource.putClubClose(body = body)
    }

}