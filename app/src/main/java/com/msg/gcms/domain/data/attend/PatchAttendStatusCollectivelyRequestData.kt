package com.msg.gcms.domain.data.attend

data class PatchAttendStatusCollectivelyRequestData(
    val attendanceIds: List<Long>,
    val attendanceStatus: String
)