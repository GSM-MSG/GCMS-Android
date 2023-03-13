package com.msg.gcms.data.remote.dto.club_member.get_club_member

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class MemberResponse(
    @SerializedName("uuid")
    val uuid: UUID,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("grade")
    val grade: Int,
    @SerializedName("classNum")
    val `class`: Int,
    @SerializedName("number")
    val num: Int,
    @SerializedName("profileImg")
    val userImg: String?,
    @SerializedName("scope")
    val scope: String
)
