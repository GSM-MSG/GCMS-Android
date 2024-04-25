package com.msg.gcms.data.remote.dto.notification.response

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.notification.GetNotificationListResponseData
import com.msg.gcms.domain.data.notification.GetNotificationListResponseData.Notice as DomainNotice
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

    fun Notice.toDomainNotice(): DomainNotice {
        return DomainNotice(
            id = id,
            title = title,
            username = username,
            createdAt = createdAt
        )
    }
}

fun GetNotificationListResponse.toGetNotificationListResponseData(): GetNotificationListResponseData {
    return GetNotificationListResponseData(
        notices = notices.map { it.toDomainNotice() }
    )
}