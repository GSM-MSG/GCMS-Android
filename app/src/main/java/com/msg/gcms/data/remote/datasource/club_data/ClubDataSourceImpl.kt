package com.msg.gcms.data.remote.datasource.club_data

import com.msg.gcms.data.remote.dto.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.club.response.SummaryClubResponse
import com.msg.gcms.data.remote.network.api.ClubAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import javax.inject.Inject

class ClubDataSourceImpl @Inject constructor(
    private val service: ClubAPI
) : ClubDataSource {

    override suspend fun getClubList(type: String): List<SummaryClubResponse> {
        return GCMSApiHandler<List<SummaryClubResponse>>()
            .httpRequest { service.getClubList(type = type) }
            .sendRequest()
    }

    override suspend fun getDetail(clubId: Long): ClubInfoResponse {
        return GCMSApiHandler<ClubInfoResponse>()
            .httpRequest { service.getDetail(clubId) }
            .sendRequest()
    }

    override suspend fun postCreateClub(body: CreateClubRequest) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.postCreateClub(body = body) }
            .sendRequest()
    }

    override suspend fun putChangeClub(body: ModifyClubInfoRequest, clubId: Long) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.putChangeClub(body = body, clubId = clubId)}
            .sendRequest()
    }

    override suspend fun exitClub(clubId: Long) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.exitClub(clubId = clubId) }
            .sendRequest()
    }

    override suspend fun deleteClub(clubId: Long) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.deleteClub(clubId = clubId) }
            .sendRequest()
    }

    override suspend fun putClubOpen(clubId: Long) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.putClubOpen(clubId = clubId) }
            .sendRequest()
    }

    override suspend fun putClubClose(clubId: Long) {
        return GCMSApiHandler<Unit>()
            .httpRequest { service.putClubClose(clubId = clubId) }
            .sendRequest()

    }
}