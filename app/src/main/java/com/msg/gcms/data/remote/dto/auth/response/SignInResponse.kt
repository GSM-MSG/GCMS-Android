package com.msg.gcms.data.remote.dto.auth.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("accessExp")
    val accessExp: String,
    @SerializedName("refreshExp")
    val refreshExp: String
)
