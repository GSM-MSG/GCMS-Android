package com.msg.gcms.domain.data.club


data class MemberListData(
    val uuid: String,
    val email: String,
    val name: String,
    val grade: Int,
    val `class`: Int,
    val num: Int,
    val userImg: String?,
    val scope: String
)
