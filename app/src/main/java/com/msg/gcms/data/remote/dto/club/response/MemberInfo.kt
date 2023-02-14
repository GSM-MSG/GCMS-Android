package com.msg.gcms.data.remote.dto.club.response

import com.google.gson.annotations.SerializedName

data class MemberInfo(
    @SerializedName("userScope")
    val userScope: String,
    @SerializedName("clubMember")
    val requestUser: List<MemberSummaryResponse>
)
