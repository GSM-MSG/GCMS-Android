package com.msg.gcms.data.remote.dto.datasource.user.response

import com.msg.gcms.data.remote.dto.datasource.club.response.UserInfo

data class UserInfoResponse (
    val userData: UserData,
    val clubs: List<ClubData>,
)

