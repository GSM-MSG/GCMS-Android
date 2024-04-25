package com.msg.gcms.domain.data.notification

import java.time.LocalDateTime

data class GetDetailNotificationResponseData(
    val title: String,
    val content: String,
    val username: String,
    val userProfileImg: String,
    val createdAt: LocalDateTime
)
