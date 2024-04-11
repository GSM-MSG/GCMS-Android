package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.attend.AttendDataSource
import com.msg.gcms.data.remote.dto.attend.request.PatchAttendStatusRequest
import com.msg.gcms.data.remote.dto.attend.request.PostAttendListRequest
import com.msg.gcms.data.remote.dto.attend.response.toGetClubAttendListResponseData
import com.msg.gcms.domain.data.attend.GetClubAttendListResponseData
import com.msg.gcms.domain.data.attend.PatchAttendStatusRequestData
import com.msg.gcms.domain.data.attend.PostAttendListRequestData
import com.msg.gcms.domain.repository.AttendRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class AttendRepositoryImpl @Inject constructor(
    private val attendDataSource: AttendDataSource
) : AttendRepository {
    override suspend fun getClubAttendList(
        clubId: Long,
        date: LocalDate,
        period: String
    ): Flow<GetClubAttendListResponseData> {
        return attendDataSource.getClubAttendList(
            clubId = clubId,
            date = date,
            period = period
        ).map {
            it.toGetClubAttendListResponseData()
        }
    }

    override suspend fun postAttendList(body: PostAttendListRequestData): Flow<Unit> {
        return attendDataSource.postAttendList(
            body = PostAttendListRequest(
                name = body.name,
                date = body.date,
                periods = body.periods
            )
        )
    }

    override suspend fun patchAttendStatus(body: PatchAttendStatusRequestData): Flow<Unit> {
        return attendDataSource.patchAttendStatus(
            body = PatchAttendStatusRequest(
                attendanceId = body.attendanceId,
                attendanceStatus = body.attendanceStatus
            )
        )
    }
}