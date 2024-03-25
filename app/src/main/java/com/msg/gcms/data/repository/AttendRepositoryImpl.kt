package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.attend.AttendDataSource
import com.msg.gcms.data.remote.dto.attend.response.toGetClubAttendListResponseData
import com.msg.gcms.domain.data.attend.GetClubAttendListResponseData
import com.msg.gcms.domain.repository.AttendRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class AttendRepositoryImpl @Inject constructor(
    private val attendDataSource: AttendDataSource
) : AttendRepository {
    override suspend fun getClubAttendList(
        clubId: Long,
        date: LocalDate,
        period: LocalTime
    ): GetClubAttendListResponseData {
        return attendDataSource.getClubAttendList(
            clubId = clubId,
            date = date,
            period = period
        ).toGetClubAttendListResponseData()
    }
}