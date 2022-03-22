package com.msg.gcms.data.remote.dto.datasource.auth

data class Verify(
    val email: String,
    val code: String
)