package com.msg.gcms.data.remote.dto.club_member.get_club_member

import com.google.gson.annotations.SerializedName

data class GetClubMemberResponse(
    @SerializedName("userScope")
    val userScope: String,
    @SerializedName("clubMember")
    val requestUser: List<MemberResponse>
)
