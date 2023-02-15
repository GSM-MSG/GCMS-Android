package com.msg.gcms.domain.data.club.get_club_member

data class GetClubMemberResponse(
    val userScope: String,
    val requestUser: List<MemberData>
)
