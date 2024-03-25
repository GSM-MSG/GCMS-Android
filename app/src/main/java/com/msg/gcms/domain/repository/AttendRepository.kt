package com.msg.gcms.domain.repository

import com.msg.gcms.domain.data.attend.GetClubAttendListResponseData
import java.time.LocalDate
import java.time.LocalTime

interface AttendRepository {
    suspend fun getClubAttendList(clubId: Long, date: LocalDate, period: LocalTime): GetClubAttendListResponseData
}