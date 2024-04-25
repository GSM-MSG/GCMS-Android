package com.msg.gcms.domain.data.attend

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class GetClubAttendListResponseData(
    val date: LocalDate,
    val period: String,
    val users: List<User>
) {
    data class User(
        val id: UUID,
        val attendanceId: Long,
        val name: String,
        val grade: Int,
        val classNum: Int,
        val number: Int,
        val attendanceStatus: String
    )
}
