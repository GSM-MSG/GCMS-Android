package com.msg.gcms.domain.usecase.notification

import com.msg.gcms.domain.repository.NotificationRepository
import javax.inject.Inject

class DeleteNoticeUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(id: Long) = runCatching {
        notificationRepository.deleteNotice(id = id)
    }
}