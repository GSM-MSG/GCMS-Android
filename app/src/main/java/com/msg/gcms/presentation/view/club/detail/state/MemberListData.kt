package com.msg.gcms.presentation.view.club.detail.state


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
