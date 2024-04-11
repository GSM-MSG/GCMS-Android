package com.msg.gcms.data.remote.datasource.attend

import com.msg.gcms.data.remote.dto.attend.request.PatchAttendStatusRequest
import com.msg.gcms.data.remote.dto.attend.request.PostAttendListRequest
import com.msg.gcms.data.remote.dto.attend.response.GetClubAttendListResponse
import com.msg.gcms.data.remote.network.api.AttendAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class AttendDataSourceImpl @Inject constructor(
    private val attendAPI: AttendAPI
) : AttendDataSource {
    override suspend fun getClubAttendList(
        clubId: Long,
        date: LocalDate,
        period: String
    ): Flow<GetClubAttendListResponse> = flow {
        emit(
            GCMSApiHandler<GetClubAttendListResponse>()
                .httpRequest {
                    attendAPI.getAttendList(
                        clubId = clubId,
                        date = date,
                        period = period
                    )
                }
                .sendRequest()
        )
    }

    override suspend fun postAttendList(body: PostAttendListRequest): Flow<Unit> = flow {
        emit(
            GCMSApiHandler<Unit>()
                .httpRequest {
                    attendAPI.postAttendList(
                        body = body
                    )
                }
                .sendRequest()
        )
    }

    override suspend fun patchAttendStatus(body: PatchAttendStatusRequest): Flow<Unit> = flow {
        emit(
            GCMSApiHandler<Unit>()
                .httpRequest {
                    attendAPI.patchAttendStatus(
                        body = body
                    )
                }
                .sendRequest()
        )
    }
}