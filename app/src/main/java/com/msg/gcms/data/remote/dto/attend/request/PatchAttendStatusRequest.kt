package com.msg.gcms.data.remote.dto.attend.request

import com.google.gson.annotations.SerializedName

data class PatchAttendStatusRequest(
    @SerializedName("attendanceId")
    val attendanceId: Long,
    @SerializedName("attendanceStatus")
    val attendanceStatus: String
)
