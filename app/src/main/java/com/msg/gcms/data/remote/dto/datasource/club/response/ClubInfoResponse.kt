package com.msg.gcms.data.remote.dto.datasource.club.response

data class ClubInfoResponse(
    val club: ClubResponse,
    val activityUrls: List<String>,
    val head: UserInfo,
    val member: List<UserInfo>,
    val scope: String,
    val isApplied: Boolean
)
