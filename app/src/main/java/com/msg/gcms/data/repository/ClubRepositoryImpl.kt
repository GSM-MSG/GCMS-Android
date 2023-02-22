package com.msg.gcms.data.repository

import com.msg.gcms.data.mapper.ClubMapper
import com.msg.gcms.data.remote.datasource.club.ClubDataSource
import com.msg.gcms.data.remote.dto.club.create_club.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.modify_club_info.ModifyClubInfoRequest
import com.msg.gcms.domain.data.club.get_club_detail.ClubDetailData
import com.msg.gcms.domain.data.club.get_club_list.GetClubListData
import com.msg.gcms.domain.data.club.modify_club_info.ModifyClubInfoData
import com.msg.gcms.domain.data.club.create_club.CreateClubData
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ClubRepositoryImpl @Inject constructor(
    private val dataSource: ClubDataSource
) : ClubRepository {
    override suspend fun getClubList(type: String): List<GetClubListData> {
        return ClubMapper.mapperToGetClubListData(dataSource.getClubList(type = type))
    }

    override suspend fun getDetail(clubId: Long): ClubDetailData {
        return ClubMapper.mapperToDetailData(dataSource.getDetail(clubId))
    }

    override suspend fun postCreateClub(body: CreateClubData) {
        return dataSource.postCreateClub(
            body = CreateClubRequest(
                activityUrls = body.activityUrls,
                bannerUrl = body.bannerUrl,
                contact = body.contact,
                description = body.description,
                member = body.member,
                notionLink = body.notionLink,
                teacher = body.teacher,
                title = body.title,
                type = body.type
            )
        )
    }

    override suspend fun putChangeClub(body: ModifyClubInfoData, clubId: Long) {
        return dataSource.putChangeClub(
            body = ModifyClubInfoRequest(
                type = body.type,
                activityImgs = body.activityImgs,
                bannerUrl = body.bannerUrl,
                contact = body.contact,
                description = body.description,
                member = body.member,
                notionLink = body.notionLink,
                teacher = body.teacher,
                title = body.title
            ), clubId = clubId
        )
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