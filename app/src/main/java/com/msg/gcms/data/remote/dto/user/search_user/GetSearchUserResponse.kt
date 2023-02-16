package com.msg.gcms.data.remote.dto.user.search_user

import com.google.gson.annotations.SerializedName

data class GetSearchUserResponse(
    @SerializedName("uuid")
    val uuid: String,
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
    val profileImg: String
)
