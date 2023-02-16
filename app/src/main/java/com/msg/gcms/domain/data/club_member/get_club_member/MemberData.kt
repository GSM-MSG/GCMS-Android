package com.msg.gcms.domain.data.club_member.get_club_member


data class MemberData(
    val uuid: String,
    val email: String,
    val name: String,
    val grade: Int,
    val `class`: Int,
    val num: Int,
    val userImg: String?,
    val scope: String
)
