package com.msg.gcms.domain.usecase.notification

import com.msg.gcms.domain.data.notification.PostWriteNotificationRequestData
import com.msg.gcms.domain.repository.NotificationRepository
import javax.inject.Inject

class PostWriteNoticeUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(clubId: Long, body: PostWriteNotificationRequestData) = runCatching {
        notificationRepository.postWriteNotice(
            clubId = clubId,
            body = body
        )
    }
}