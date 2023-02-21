package com.msg.gcms.data.remote.dto.user.get_my_profile

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class GetMyProfileResponse(
    @SerializedName("uuid")
    val uuid: UUID,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("grade")
    val grade: Int,
    @SerializedName("classNum")
    val classNum: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("profileImg")
    val profileImg: String?,
    @SerializedName("clubs")
    val clubs: List<ProfileClubResponse>
)
