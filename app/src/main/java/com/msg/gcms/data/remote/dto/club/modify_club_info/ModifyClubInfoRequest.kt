package com.msg.gcms.data.remote.dto.club.modify_club_info

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ModifyClubInfoRequest(
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("content")
    val description: String,
    @SerializedName("bannerImg")
    val bannerUrl: String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("notionLink")
    val notionLink: String?,
    @SerializedName("teacher")
    val teacher: String?,
    @SerializedName("activityImgs")
    val activityImgs: List<String>,
    @SerializedName("member")
    val member: List<UUID>

)
