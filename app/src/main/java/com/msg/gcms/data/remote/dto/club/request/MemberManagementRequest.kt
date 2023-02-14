package com.msg.gcms.data.remote.dto.club.request

import com.google.gson.annotations.SerializedName

data class MemberManagementRequest(
    @SerializedName("uuid")
    val uuid: String
)
