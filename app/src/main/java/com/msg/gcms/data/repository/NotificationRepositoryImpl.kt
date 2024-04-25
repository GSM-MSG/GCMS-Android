package com.msg.gcms.data.repository

import com.msg.gcms.data.remote.datasource.notification.NotificationDataSource
import com.msg.gcms.data.remote.dto.notification.request.PatchModifyNotificationRequest
import com.msg.gcms.data.remote.dto.notification.request.PostWriteNotificationRequest
import com.msg.gcms.data.remote.dto.notification.response.GetDetailNotificationResponse
import com.msg.gcms.data.remote.dto.notification.response.toDetailNotificationData
import com.msg.gcms.data.remote.dto.notification.response.toGetNotificationListResponseData
import com.msg.gcms.domain.data.notification.GetDetailNotificationResponseData
import com.msg.gcms.domain.data.notification.GetNotificationListResponseData
import com.msg.gcms.domain.data.notification.PatchModifyNotificationRequestData
import com.msg.gcms.domain.data.notification.PostWriteNotificationRequestData
import com.msg.gcms.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository {
    override suspend fun postWriteNotice(
        clubId: Long,
        body: PostWriteNotificationRequestData
    ): Flow<Unit> {
        return notificationDataSource.postWriteNotice(
            clubId = clubId,
            body = PostWriteNotificationRequest(
                title = body.title,
                content = body.content
            )
        )
    }

    override suspend fun getNoticeList(clubId: Long): Flow<GetNotificationListResponseData> {
        return notificationDataSource.getNoticeList(
            clubId = clubId
        ).map {
            it.toGetNotificationListResponseData()
        }
    }

    override suspend fun getDetailNotice(id: Long): Flow<GetDetailNotificationResponseData> {
        return notificationDataSource.getDetailNotice(id = id).map {
            it.toDetailNotificationData()
        }
    }

    override suspend fun patchNotice(
        id: Long,
        body: PatchModifyNotificationRequestData
    ): Flow<Unit> {
        return notificationDataSource.patchNotice(
            id = id,
            body = PatchModifyNotificationRequest(
                title = body.title,
                content = body.content
            )
        )
    }

    override suspend fun deleteNotice(id: Long): Flow<Unit> {
        return notificationDataSource.deleteNotice(
            id = id
        )
    }
}