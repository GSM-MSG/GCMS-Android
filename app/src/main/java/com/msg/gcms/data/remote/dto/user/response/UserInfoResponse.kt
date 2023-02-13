package com.msg.gcms.data.remote.dto.user.response

import com.google.gson.annotations.SerializedName

data class UserInfoResponse (
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("grade")
    val grade: String,
    @SerializedName("classNum")
    val classNum: String,
    @SerializedName("number")
    val number: String,
    @SerializedName("profileImg")
    val profileImg: String,
    @SerializedName("clubs")
    val clubs: List<SummarizedClubInfo>,
)

