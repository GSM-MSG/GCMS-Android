package com.msg.gcms.data.remote.dto.datasource.club.response

import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import java.io.Serializable

data class ClubInfoResponse(
    val club: ClubResponse,
    val activityUrls: List<String>,
    val head: UserInfo,
    val member: List<UserData>,
    val scope: String,
    val isApplied: Boolean
): Serializable
