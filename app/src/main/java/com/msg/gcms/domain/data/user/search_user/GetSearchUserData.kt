package com.msg.gcms.domain.data.user.search_user

import java.util.UUID

data class GetSearchUserData(
    val uuid: UUID,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImg: String
)
