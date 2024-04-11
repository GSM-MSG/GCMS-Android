package com.msg.gcms.data.remote.dto.attend.request

import com.google.gson.annotations.SerializedName

data class PatchAttendStatusCollectivelyRequest(
    @SerializedName("attendanceIds")
    val attendanceIds: List<Long>,
    @SerializedName("attendanceStatus")
    val attendanceStatus: String
)
