package com.msg.gcms.domain.data.club_member.get_club_member

data class GetClubMemberData(
    val userScope: String,
    val requestUser: List<MemberData>
)
