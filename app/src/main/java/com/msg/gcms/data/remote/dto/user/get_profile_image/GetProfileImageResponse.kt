package com.msg.gcms.data.remote.dto.user.get_profile_image

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.user.get_profile_image.GetProfileImageData

data class GetProfileImageResponse(
    @SerializedName("profileImg")
    val profileImg: String?,
)

fun GetProfileImageResponse.toGetProfileImageData(): GetProfileImageData {
    return GetProfileImageData(
        profileImg = profileImg
    )
}