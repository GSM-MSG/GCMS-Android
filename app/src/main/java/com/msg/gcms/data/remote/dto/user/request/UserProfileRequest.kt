package com.msg.gcms.data.remote.dto.user.request

import com.google.gson.annotations.SerializedName

data class UserProfileRequest(
    @SerializedName("profileImg")
    val url: String
)
