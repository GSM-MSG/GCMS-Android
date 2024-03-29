package com.msg.gcms.data.remote.dto.club.get_club_list

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.club.get_club_list.GetClubListData

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

fun GetClubListResponse.toClubListData(): GetClubListData {
    return GetClubListData(
        id = id,
        bannerUrl = bannerUrl,
        title = title,
        type = type
    )
}