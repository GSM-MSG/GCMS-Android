package com.msg.gcms.data.remote.dto.notification.response

import com.google.gson.annotations.SerializedName
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
