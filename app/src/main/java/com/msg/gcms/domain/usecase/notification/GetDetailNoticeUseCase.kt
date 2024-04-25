package com.msg.gcms.domain.usecase.notification

import com.msg.gcms.domain.repository.NotificationRepository
import javax.inject.Inject

class GetDetailNoticeUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(id: Long) = runCatching {
        notificationRepository.getDetailNotice(id = id)
    }
}