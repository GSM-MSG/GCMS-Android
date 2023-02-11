package com.msg.gcms.data.remote.dto.club.request

import com.google.gson.annotations.SerializedName

data class CreateClubRequest(
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("content")
    val description: String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("notionLink")
    val notionLink: String,
    @SerializedName("teacher")
    val teacher: String?,
    @SerializedName("member")
    val member: List<String>?,
    @SerializedName("activityImgs")
    val activityUrls: List<String>?,
    @SerializedName("bannerImg")
    val bannerUrl:String
)
