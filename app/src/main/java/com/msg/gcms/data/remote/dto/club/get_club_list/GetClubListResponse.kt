package com.msg.gcms.data.remote.dto.club.get_club_list

import com.google.gson.annotations.SerializedName

data class GetClubListResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("bannerImg")
    val bannerUrl: String
)
