package com.msg.gcms.data.remote.dto.auth.request

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("code")
    val code: String,
    @SerializedName("token")
    val token: String,
)
