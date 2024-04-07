package com.msg.gcms.data.remote.datasource.attend

import com.msg.gcms.data.remote.dto.attend.request.PostAttendListRequest
import com.msg.gcms.data.remote.dto.attend.response.GetClubAttendListResponse
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AttendDataSource {
    suspend fun getClubAttendList(clubId: Long, date: LocalDate, period: String): Flow<GetClubAttendListResponse>
    suspend fun postAttendList(body: PostAttendListRequest): Flow<Unit>
}