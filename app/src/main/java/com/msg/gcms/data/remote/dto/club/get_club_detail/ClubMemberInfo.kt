package com.msg.gcms.data.remote.dto.club.get_club_detail

import com.google.gson.annotations.SerializedName

data class ClubMemberInfo(
    @SerializedName("uuid")
    val uuid: String,
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
    @SerializedName("userImg")
    val userImg: String?,
)
