package com.msg.gcms.domain.repository

import com.msg.gcms.domain.data.attend.GetClubAttendListResponseData
import com.msg.gcms.domain.data.attend.PostAttendListRequestData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

interface AttendRepository {
    suspend fun getClubAttendList(clubId: Long, date: LocalDate, period: String): Flow<GetClubAttendListResponseData>
    suspend fun postAttendList(body: PostAttendListRequestData): Flow<Unit>
}