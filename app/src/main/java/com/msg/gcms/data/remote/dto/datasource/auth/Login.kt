package com.msg.gcms.data.remote.dto.datasource.auth

data class Login(
    val email: String,
    val password: String,
    val accessToken: String,
    val refreshToken: String,
    val expiredAt: String
)