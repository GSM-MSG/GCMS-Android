package com.msg.gcms.data.remote.dto.user.response

import java.io.Serializable

data class UserData(
    val uuid: String,
    val email: String,
    val name: String,
    val grade: Int,
    val `class`: Int,
    val num: Int,
    val userImg: String
): Serializable
