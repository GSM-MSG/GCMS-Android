package com.msg.gcms.data.remote.dto.auth.response

data class SignInResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessExp: String,
    val refreshExp: String
)
