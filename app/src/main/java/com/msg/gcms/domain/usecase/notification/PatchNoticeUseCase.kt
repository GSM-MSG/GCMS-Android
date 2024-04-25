package com.msg.gcms.domain.usecase.notification

import com.msg.gcms.domain.data.notification.PatchModifyNotificationRequestData
import com.msg.gcms.domain.repository.NotificationRepository
import javax.inject.Inject

class PatchNoticeUseCase @Inject constructor(
    private val  notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(id: Long, body: PatchModifyNotificationRequestData) = runCatching {
        notificationRepository.patchNotice(
            id = id,
            body = body
        )
    }
}