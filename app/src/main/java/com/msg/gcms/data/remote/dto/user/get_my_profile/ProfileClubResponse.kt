package com.msg.gcms.data.remote.dto.user.get_my_profile

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.user.get_my_profile.ProfileClubData

data class ProfileClubResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("type")
    val type: String,
    @SerializedName("bannerImg")
    val bannerImg: String,
    @SerializedName("name")
    val title: String
)

fun ProfileClubResponse.toProfileClubData(): ProfileClubData {
    return ProfileClubData(
        id = id,
        type = type,
        bannerImg = bannerImg,
        title = title
    )
}