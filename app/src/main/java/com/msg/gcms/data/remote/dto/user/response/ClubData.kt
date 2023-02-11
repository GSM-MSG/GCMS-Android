package com.msg.gcms.data.remote.dto.user.response

data class ClubData(
    val id: Long,
    val type: String,
    val bannerUrl: String,
    val title: String,
    val description: String,
    val contact: String,
    val teacher: String?,
    val isOpened: Boolean?
)