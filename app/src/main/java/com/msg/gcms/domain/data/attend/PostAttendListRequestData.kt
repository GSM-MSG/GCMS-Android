package com.msg.gcms.domain.data.attend

import java.time.LocalDate

data class PostAttendListRequestData(
    val name: String,
    val date: LocalDate,
    val periods: List<String>
)