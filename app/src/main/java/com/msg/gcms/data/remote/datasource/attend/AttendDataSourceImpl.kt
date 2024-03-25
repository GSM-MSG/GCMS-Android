package com.msg.gcms.data.remote.datasource.attend

import com.msg.gcms.data.remote.dto.attend.response.GetClubAttendListResponse
import com.msg.gcms.data.remote.network.api.AttendAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class AttendDataSourceImpl @Inject constructor(
    private val attendAPI: AttendAPI
) : AttendDataSource {
    override suspend fun getClubAttendList(
        clubId: Long,
        date: LocalDate,
        period: LocalTime
    ): GetClubAttendListResponse {
        return GCMSApiHandler<GetClubAttendListResponse>()
            .httpRequest {
                attendAPI.getAttendList(
                    clubId = clubId,
                    date = date,
                    period = period
                )
            }
            .sendRequest()
    }
}