package com.msg.gcms.data.remote.dto.club.response

import com.google.gson.annotations.SerializedName

data class SummaryClubResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("bannerImg")
    val bannerUrl: String
)
