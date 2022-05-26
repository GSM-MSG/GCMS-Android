package com.msg.gcms.data.remote.dto.datasource.auth.response

data class RegisterResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiredAt: String
)
