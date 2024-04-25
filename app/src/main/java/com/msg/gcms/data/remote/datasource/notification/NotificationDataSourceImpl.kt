package com.msg.gcms.data.remote.datasource.notification

import com.msg.gcms.data.remote.dto.notification.request.PatchModifyNotificationRequest
import com.msg.gcms.data.remote.dto.notification.request.PostWriteNotificationRequest
import com.msg.gcms.data.remote.dto.notification.response.GetDetailNotificationResponse
import com.msg.gcms.data.remote.dto.notification.response.GetNotificationListResponse
import com.msg.gcms.data.remote.network.api.NotificationAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val noticeService: NotificationAPI
) : NotificationDataSource {
    override suspend fun postWriteNotice(clubId: Long, body: PostWriteNotificationRequest): Flow<Unit> = flow {
        emit(
            GCMSApiHandler<Unit>()
                .httpRequest {
                    noticeService.writeNotification(
                        clubId = clubId,
                        body = body
                    )
                }
                .sendRequest()
        )
    }

    override suspend fun getNoticeList(clubId: Long): Flow<GetNotificationListResponse> = flow {
        emit(
            GCMSApiHandler<GetNotificationListResponse>()
                .httpRequest {
                    noticeService.getNoticeList(clubId = clubId)
                }
                .sendRequest()
        )
    }

    override suspend fun getDetailNotice(id: Long): Flow<GetDetailNotificationResponse> = flow {
        emit(
            GCMSApiHandler<GetDetailNotificationResponse>()
                .httpRequest {
                    noticeService.getDetailNotification(id = id)
                }
                .sendRequest()
        )
    }

    override suspend fun patchNotice(id: Long, body: PatchModifyNotificationRequest): Flow<Unit> = flow {
        emit(
            GCMSApiHandler<Unit>()
                .httpRequest {
                    noticeService.patchNotification(
                        id = id,
                        body = body
                    )
                }
                .sendRequest()
        )
    }

    override suspend fun deleteNotice(id: Long): Flow<Unit> = flow {
        emit(
            GCMSApiHandler<Unit>()
                .httpRequest {
                    noticeService.deleteNotification(id = id)
                }
                .sendRequest()
        )
    }
}