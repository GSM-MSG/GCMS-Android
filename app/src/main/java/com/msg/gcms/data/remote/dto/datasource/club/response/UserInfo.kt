package com.msg.gcms.data.remote.dto.datasource.club.response

import java.io.Serializable

data class UserInfo(
    val email: String,
    val grade: Int,
    val `class`: Int,
    val num: Int,
    val userImg: String?,
    val name: String
)
