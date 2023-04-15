package com.msg.gcms.data.remote.dto.club_member.get_club_member

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.club_member.get_club_member.GetClubMemberData

data class GetClubMemberResponse(
    @SerializedName("scope")
    val userScope: String,
    @SerializedName("clubMember")
    val requestUser: List<MemberResponse>
)

fun GetClubMemberResponse.toClubMemberData(): GetClubMemberData {
    return GetClubMemberData(
        userScope = userScope,
        requestUser = requestUser.map {
            it.toMemberData()
        }
    )
}
