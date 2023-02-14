package com.msg.gcms.data.remote.dto.club.response

import com.google.gson.annotations.SerializedName

data class UserInfo(
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
