package com.msg.gcms.data.remote.dto.datasource.user.response

data class ClubData(
    val id: Int,
    val type: String,
    val bannerUrl: String,
    val title: String,
    val description: String,
    val contact: String,
    val teacher: String?,
    val isOpened: Boolean?
)