package com.msg.gcms.domain.data.notification

import java.time.LocalDateTime

data class GetNotificationListResponseData(
    val notices: List<Notice>
) {
    data class Notice(
        val id: Long,
        val title: String,
        val username: String,
        val createdAt: LocalDateTime
    )
}
