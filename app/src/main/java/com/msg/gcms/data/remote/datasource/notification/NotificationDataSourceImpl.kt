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
    override suspend fun postWriteNotice(body: PostWriteNotificationRequest): Flow<Unit> = flow {
        emit(
            GCMSApiHandler<Unit>()
                .httpRequest {
                    noticeService.writeNotification(body = body)
                }
                .sendRequest()
        )
    }

    override suspend fun getNoticeList(): Flow<GetNotificationListResponse> = flow {
        emit(
            GCMSApiHandler<GetNotificationListResponse>()
                .httpRequest {
                    noticeService.getNoticeList()
                }
                .sendRequest()
        )
    }

    override suspend fun getDetailNotice(): Flow<GetDetailNotificationResponse> = flow {
        emit(
            GCMSApiHandler<GetDetailNotificationResponse>()
                .httpRequest {
                    noticeService.getDetailNotification()
                }
                .sendRequest()
        )
    }

    override suspend fun patchNotice(body: PatchModifyNotificationRequest): Flow<Unit> = flow {
        emit(
            GCMSApiHandler<Unit>()
                .httpRequest {
                    noticeService.patchNotification(body = body)
                }
                .sendRequest()
        )
    }

    override suspend fun deleteNotice(): Flow<Unit> = flow {
        emit(
            GCMSApiHandler<Unit>()
                .httpRequest {
                    noticeService.deleteNotification()
                }
                .sendRequest()
        )
    }
}