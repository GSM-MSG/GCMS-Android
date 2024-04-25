package com.msg.gcms.domain.usecase.notification

import com.msg.gcms.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNoticeListUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(clubId: Long) = runCatching {
        notificationRepository.getNoticeList(clubId = clubId)
    }
}