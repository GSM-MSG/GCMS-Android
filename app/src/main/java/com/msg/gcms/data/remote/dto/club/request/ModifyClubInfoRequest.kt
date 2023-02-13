package com.msg.gcms.data.remote.dto.club.request

import com.google.gson.annotations.SerializedName

data class ModifyClubInfoRequest(
    @SerializedName("type")
    val type : String,
    @SerializedName("type")
    val title : String,
    @SerializedName("type")
    val description : String,
    @SerializedName("type")
    val contact : String,
    @SerializedName("type")
    val notionLink: String?,
    @SerializedName("type")
    val teacher :String?,
    @SerializedName("type")
    val deleteActivityUrls: List<String>?,
    @SerializedName("type")
    val bannerUrl: String,
    @SerializedName("type")
    val newActivityUrls: List<String>?
)
