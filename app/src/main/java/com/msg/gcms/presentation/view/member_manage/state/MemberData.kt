package com.msg.gcms.presentation.view.member_manage.state

data class MemberData(
    val uuid: String,
    val email: String,
    val name: String,
    val grade: Int,
    val `class`: Int,
    val num: Int,
    val userImg: String,
    val scope: String
)