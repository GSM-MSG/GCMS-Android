package com.msg.gcms.data.remote.datasource.club_data

import com.msg.gcms.data.remote.dto.club.create_club.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.get_club_detail.ClubDetailResponse
import com.msg.gcms.data.remote.dto.club.get_club_list.GetClubListResponse
import com.msg.gcms.data.remote.dto.club.modify_club_info.ModifyClubInfoRequest
import com.msg.gcms.data.remote.network.api.ClubAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import javax.inject.Inject

class ClubDataSourceImpl @Inject constructor(
    private val service: ClubAPI
) : ClubDataSource {

    override suspend fun getClubList(type: String): List<GetClubListResponse> {
        return GCMSApiHandler<List<GetClubListResponse>>()
            .httpRequest { service.getClubList(type = type) }
            .sendRequest()
    }

    override suspend fun getDetail(clubId: Long): ClubDetailResponse {
        return GCMSApiHandler<ClubDetailResponse>()
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