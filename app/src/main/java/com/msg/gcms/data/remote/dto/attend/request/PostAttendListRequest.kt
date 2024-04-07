package com.msg.gcms.data.remote.dto.attend.request

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class PostAttendListRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("date")
    val date: LocalDate,
    @SerializedName("periods")
    val periods: List<String>
)
