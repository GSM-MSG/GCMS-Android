package com.msg.gcms.data.remote.dto.club_member.get_club_member

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.club.get_club_member.MemberData

data class GetClubMemberResponse(
    @SerializedName("userScope")
    val userScope: String,
    @SerializedName("clubMember")
    val requestUser: List<MemberData>
)
