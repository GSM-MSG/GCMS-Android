package com.msg.gcms.domain.data.auth

data class SignInResponseData(
    val accessToken: String,
    val refreshToken: String,
    val accessExp: String,
    val refreshExp: String
)
