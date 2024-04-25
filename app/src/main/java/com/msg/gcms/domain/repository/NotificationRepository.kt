package com.msg.gcms.domain.repository

import com.msg.gcms.domain.data.notification.GetDetailNotificationResponseData
import com.msg.gcms.domain.data.notification.GetNotificationListResponseData
import com.msg.gcms.domain.data.notification.PatchModifyNotificationRequestData
import com.msg.gcms.domain.data.notification.PostWriteNotificationRequestData
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun postWriteNotice(clubId: Long, body: PostWriteNotificationRequestData): Flow<Unit>
    suspend fun getNoticeList(clubId: Long): Flow<GetNotificationListResponseData>
    suspend fun getDetailNotice(id: Long): Flow<GetDetailNotificationResponseData>
    suspend fun patchNotice(id: Long ,body: PatchModifyNotificationRequestData): Flow<Unit>
    suspend fun deleteNotice(id: Long): Flow<Unit>
}