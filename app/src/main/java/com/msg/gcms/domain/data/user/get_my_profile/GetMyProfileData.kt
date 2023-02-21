package com.msg.gcms.domain.data.user.get_my_profile

import java.util.UUID

data class GetMyProfileData(
    val uuid: UUID,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImg: String?,
    val clubs: List<ProfileClubData>
)
