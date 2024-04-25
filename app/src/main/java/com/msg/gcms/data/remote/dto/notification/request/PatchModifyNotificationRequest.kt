package com.msg.gcms.data.remote.dto.notification.request

import com.google.gson.annotations.SerializedName

data class PatchModifyNotificationRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String
)
