package com.msg.gcms.data.remote.dto.datasource.club.response


data class MemberSummaryResponse(
    val email: String,
    val name: String,
    val grade: Int,
    val `class`: Int,
    val num: Int,
    val userImg: String,
    val scope: String
)
