package com.msg.gcms.data.remote.dto.datasource.club.response

data class MemberInfo(
    val userScope: String,
    val requestUser: List<MemberSummaryResponse>
)
