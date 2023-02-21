package com.msg.gcms.data.remote.dto.club.create_club

import com.google.gson.annotations.SerializedName

data class CreateClubRequest(
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("content")
    val description: String,
    @SerializedName("bannerImg")
    val bannerUrl:String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("notionLink")
    val notionLink: String,
    @SerializedName("teacher")
    val teacher: String?,
    @SerializedName("activityImgs")
    val activityUrls: List<String>?,
    @SerializedName("member")
    val member: List<String>?,
)
