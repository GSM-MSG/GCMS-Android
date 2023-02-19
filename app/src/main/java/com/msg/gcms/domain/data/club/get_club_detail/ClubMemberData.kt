package com.msg.gcms.domain.data.club.get_club_detail

import java.util.UUID

data class ClubMemberData(
    val uuid: UUID,
    val email: String,
    val name: String,
    val grade: Int,
    val `class`: Int,
    val num: Int,
    val userImg: String?,
)
