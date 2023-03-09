package com.msg.gcms.data.remote.dto.user.get_profile_image

import com.google.gson.annotations.SerializedName

data class GetProfileImageResponse(
    @SerializedName("profileImg")
    val profileImg: String?,
)