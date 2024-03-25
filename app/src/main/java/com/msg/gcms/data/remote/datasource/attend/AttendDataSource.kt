package com.msg.gcms.data.remote.datasource.attend

import com.msg.gcms.data.remote.dto.attend.response.GetClubAttendListResponse
import java.time.LocalDate
import java.time.LocalTime

interface AttendDataSource {
    suspend fun getClubAttendList(clubId: Long, date: LocalDate, period: LocalTime): GetClubAttendListResponse
}