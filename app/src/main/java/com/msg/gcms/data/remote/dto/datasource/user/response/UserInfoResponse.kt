package com.msg.gcms.data.remote.dto.datasource.user.response

data class UserInfoResponse (
    val userData: UserData,
    val clubs: List<ClubData>,
    val afters: List<AftersData>
)

