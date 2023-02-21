package com.msg.gcms.data.mapper

import com.msg.gcms.data.remote.dto.club.get_club_detail.ClubDetailResponse
import com.msg.gcms.data.remote.dto.club.get_club_detail.ClubMemberResponse
import com.msg.gcms.data.remote.dto.club.get_club_list.GetClubListResponse
import com.msg.gcms.domain.data.club.get_club_detail.ClubDetailData
import com.msg.gcms.domain.data.club.get_club_detail.ClubMemberData
import com.msg.gcms.domain.data.club.get_club_list.GetClubListData

object ClubMapper {

    fun mapperToGetClubListData(data: List<GetClubListResponse>): List<GetClubListData> {
        return data.map {
            GetClubListData(
                id = it.id,
                bannerUrl = it.bannerUrl,
                title = it.title,
                type = it.title
            )
        }
    }

    fun mapperToDetailData(data: ClubDetailResponse): ClubDetailData {
        return ClubDetailData(
            activityImgs = data.activityImgs,
            bannerImg = data.bannerImg,
            contact = data.contact,
            content = data.content,
            head = data.head,
            id = data.id,
            isApplied = data.isApplied,
            isOpened = data.isOpened,
            member = data.member.map { mapperToMemberData(it) },
            name = data.name,
            notionLink = data.notionLink,
            scope = data.scope,
            teacher = data.teacher,
            type = data.type

        )
    }

    private fun mapperToMemberData(data: ClubMemberResponse): ClubMemberData {
        return ClubMemberData(
            uuid = data.uuid,
            email = data.email,
            `class` = data.`class`,
            grade = data.grade,
            name = data.name,
            num = data.num,
            userImg = data.userImg
        )
    }
}