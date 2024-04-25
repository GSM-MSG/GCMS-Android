package com.msg.gcms.data.remote.dto.notification.response

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.notification.GetDetailNotificationResponseData
import java.time.LocalDateTime

data class GetDetailNotificationResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("userProfileImg")
    val userProfileImg: String,
    @SerializedName("createdAt")
    val createdAt: LocalDateTime
)

fun GetDetailNotificationResponse.toDetailNotificationData(): GetDetailNotificationResponseData {
    return GetDetailNotificationResponseData(
        title = title,
        content = content,
        username = username,
        userProfileImg = userProfileImg,
        createdAt = createdAt
    )
}
