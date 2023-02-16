package com.msg.gcms.data.remote.dto.user.modify_profile_image

import com.google.gson.annotations.SerializedName

data class ModifyProfileImageRequest(
    @SerializedName("profileImg")
    val url: String
)
