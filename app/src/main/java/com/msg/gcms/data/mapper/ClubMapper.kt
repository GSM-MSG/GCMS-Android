package com.msg.gcms.data.mapper

import com.msg.gcms.data.remote.dto.club.get_club_list.GetClubListResponse
import com.msg.gcms.domain.data.club.get_club_list.GetClubListData
import com.msg.gcms.data.remote.dto.club_member.get_club_member.MemberResponse
import com.msg.gcms.domain.data.club_member.get_club_member.MemberData

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

    private fun mapperToMemberData(data: MemberResponse): MemberData {
        return MemberData(
            uuid = data.uuid,
            email = data.email,
            `class` = data.`class`,
            grade = data.grade,
            name = data.name,
            num = data.num,
            scope = data.scope,
            userImg = data.userImg
        )
    }
}