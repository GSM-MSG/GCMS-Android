package com.msg.gcms.domain.data.attend

data class PatchAttendStatusRequestData(
    val attendanceId: Long,
    val attendanceStatus: String
)
