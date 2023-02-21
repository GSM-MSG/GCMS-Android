package com.msg.gcms.domain.data.user.get_my_profile

data class GetMyProfileData(
    val uuid: String,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImg: String,
    val clubs: List<ProfileClubData>
)
