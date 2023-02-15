package com.msg.gcms.data.remote.dto.club_member.member_expelled

import com.google.gson.annotations.SerializedName

data class MemberExpelledRequest(
    @SerializedName("uuid")
    val uuid: String
)
