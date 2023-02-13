package com.msg.gcms.data.remote.dto.user.response

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("gradle")
    val grade: Int,
    @SerializedName("classNum")
    val `class`: Int,
    @SerializedName("number")
    val num: Int,
    @SerializedName("profileImg")
    val userImg: String
)
