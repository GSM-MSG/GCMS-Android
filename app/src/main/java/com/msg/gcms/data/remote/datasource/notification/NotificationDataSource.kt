package com.msg.gcms.data.remote.datasource.notification

import com.msg.gcms.data.remote.dto.notification.request.PatchModifyNotificationRequest
import com.msg.gcms.data.remote.dto.notification.request.PostWriteNotificationRequest
import com.msg.gcms.data.remote.dto.notification.response.GetDetailNotificationResponse
import com.msg.gcms.data.remote.dto.notification.response.GetNotificationListResponse
import kotlinx.coroutines.flow.Flow

interface NotificationDataSource {
    suspend fun postWriteNotice(body: PostWriteNotificationRequest): Flow<Unit>
    suspend fun getNoticeList(): Flow<GetNotificationListResponse>
    suspend fun getDetailNotice(): Flow<GetDetailNotificationResponse>
    suspend fun patchNotice(body: PatchModifyNotificationRequest): Flow<Unit>
    suspend fun deleteNotice(): Flow<Unit>
}