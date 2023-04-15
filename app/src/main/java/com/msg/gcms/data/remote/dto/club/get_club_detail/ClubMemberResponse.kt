package com.msg.gcms.data.remote.dto.club.get_club_detail

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.club.get_club_detail.ClubMemberData
import java.util.UUID

data class ClubMemberResponse(
    @SerializedName("uuid")
    val uuid: UUID,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("grade")
    val grade: Int,
    @SerializedName("class")
    val `class`: Int,
    @SerializedName("num")
    val num: Int,
    @SerializedName("profileImg")
    val userImg: String?,
)

fun ClubMemberResponse.toClubMemberData(): ClubMemberData {
    return ClubMemberData(
        uuid = uuid,
        email = email,
        `class` = `class`,
        grade = grade,
        name = name,
        num = num,
        userImg = userImg
    )
}
