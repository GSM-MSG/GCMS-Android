package com.msg.gcms.data.repository

import com.msg.gcms.data.local.datasource.club.ClubLocalDataSource
import com.msg.gcms.data.mapper.ClubMapper
import com.msg.gcms.data.remote.datasource.club.ClubDataSource
import com.msg.gcms.data.remote.dto.club.create_club.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.modify_club_info.ModifyClubInfoRequest
import com.msg.gcms.domain.data.club.create_club.CreateClubData
import com.msg.gcms.domain.data.club.get_club_detail.ClubDetailData
import com.msg.gcms.domain.data.club.get_club_list.GetClubListData
import com.msg.gcms.domain.data.club.modify_club_info.ModifyClubInfoData
import com.msg.gcms.domain.repository.ClubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClubRepositoryImpl @Inject constructor(
    private val remoteDataSource: ClubDataSource,
    private val localDataSource: ClubLocalDataSource
) : ClubRepository {
    override suspend fun getClubList(type: String): Flow<List<GetClubListData>> {
        return flow {
            emit(ClubMapper.mapperToGetClubListData(remoteDataSource.getClubList(type = type)))
        }
    }

    override suspend fun getDetail(clubId: Long): ClubDetailData {
        return ClubMapper.mapperToDetailData(remoteDataSource.getDetail(clubId))
    }

    override suspend fun postCreateClub(body: CreateClubData) {
        return remoteDataSource.postCreateClub(
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
        return remoteDataSource.putChangeClub(
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
        return remoteDataSource.deleteClub(clubId = clubId)
    }

    override suspend fun putClubOpen(clubId: Long) {
        return remoteDataSource.putClubOpen(clubId = clubId)
    }

    override suspend fun putClubClose(clubId: Long) {
        return remoteDataSource.putClubClose(clubId = clubId)
    }

    override suspend fun exitClub(clubId: Long) {
        return remoteDataSource.exitClub(clubId = clubId)
    }
}