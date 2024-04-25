package com.msg.gcms.data.remote.dto.notification.response

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class GetNotificationListResponse(
    @SerializedName("notices")
    val notices: List<Notice>
) {
    data class Notice(
        @SerializedName("id")
        val id: Long,
        @SerializedName("title")
        val title: String,
        @SerializedName("username")
        val username: String,
        @SerializedName("createdAt")
        val createdAt: LocalDateTime
    )
}