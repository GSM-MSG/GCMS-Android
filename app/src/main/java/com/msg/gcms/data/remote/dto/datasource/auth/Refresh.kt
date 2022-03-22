package com.msg.gcms.data.remote.dto.datasource.auth

data class Refresh(
    val Authorization: String,
    val accessToken: String,
    val refreshToken: String,
    val expiredAt: String
)