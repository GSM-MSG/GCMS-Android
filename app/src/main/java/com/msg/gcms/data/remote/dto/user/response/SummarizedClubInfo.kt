package com.msg.gcms.data.remote.dto.user.response

import com.google.gson.annotations.SerializedName

data class SummarizedClubInfo (
    @SerializedName("id")
    val id: Long,
    @SerializedName("type")
    val type: String,
    @SerializedName("bannerImg")
    val bannerImg: String,
    @SerializedName("title")
    val title: String,
)