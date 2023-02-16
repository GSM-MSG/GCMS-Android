package com.msg.gcms.domain.data.user.search_user

data class GetSearchUserData(
    val uuid: String,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImg: String
)
