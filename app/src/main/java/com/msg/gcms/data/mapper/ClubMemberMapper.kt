package com.msg.gcms.data.mapper

import com.msg.gcms.data.remote.dto.club_member.get_club_member.GetClubMemberResponse
import com.msg.gcms.data.remote.dto.club_member.get_club_member.MemberResponse
import com.msg.gcms.domain.data.club_member.get_club_member.GetClubMemberData
import com.msg.gcms.domain.data.club_member.get_club_member.MemberData

object ClubMemberMapper {

    fun mapperToClubMemberData(data: GetClubMemberResponse): GetClubMemberData {
        return GetClubMemberData(
            userScope = data.userScope,
            requestUser = data.requestUser.map {
                mapperToMemberData(it)
            }
        )
    }

    private fun mapperToMemberData(data: MemberResponse): MemberData {
        return MemberData(
            uuid = data.uuid,
            email = data.email,
            name = data.name,
            grade = data.grade,
            `class` = data.`class`,
            num = data.num,
            userImg = data.userImg,
            scope = data.scope
        )
    }
}