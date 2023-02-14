package com.msg.gcms.data.remote.dto.club.response

import com.google.gson.annotations.SerializedName

data class ClubInfoResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("type")
    val type: String,
    @SerializedName("bannerImg")
    val bannerImg: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("teacher")
    val teacher: String,
    @SerializedName("head")
    val head: UserInfo,
    @SerializedName("member")
    val member: List<UserInfo>,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("isApplied")
    val isApplied: Boolean
)
