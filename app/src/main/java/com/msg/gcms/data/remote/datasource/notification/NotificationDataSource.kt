package com.msg.gcms.data.remote.datasource.notification

import com.msg.gcms.data.remote.dto.notification.request.PatchModifyNotificationRequest
import com.msg.gcms.data.remote.dto.notification.request.PostWriteNotificationRequest
import com.msg.gcms.data.remote.dto.notification.response.GetDetailNotificationResponse
import com.msg.gcms.data.remote.dto.notification.response.GetNotificationListResponse
import kotlinx.coroutines.flow.Flow

interface NotificationDataSource {
    suspend fun postWriteNotice(clubId: Long, body: PostWriteNotificationRequest): Flow<Unit>
    suspend fun getNoticeList(clubId: Long): Flow<GetNotificationListResponse>
    suspend fun getDetailNotice(id: Long): Flow<GetDetailNotificationResponse>
    suspend fun patchNotice(id: Long ,body: PatchModifyNotificationRequest): Flow<Unit>
    suspend fun deleteNotice(id: Long): Flow<Unit>
}