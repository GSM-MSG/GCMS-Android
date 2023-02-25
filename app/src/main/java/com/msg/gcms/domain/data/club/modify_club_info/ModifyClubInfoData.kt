package com.msg.gcms.domain.data.club.modify_club_info

import java.util.UUID

data class ModifyClubInfoData(
    val type: String,
    val title: String,
    val description: String,
    val bannerUrl: String,
    val contact: String,
    val notionLink: String?,
    val teacher: String?,
    val activityImgs: List<String>,
    val member: List<UUID>
)
