package com.msg.gcms.data.remote.dto.user.response

data class UserInfoResponse (
    val userData: UserData,
    val clubs: List<ClubData>,
    val afterSchools: List<AftersData>
)

