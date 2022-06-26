package com.msg.gcms.data.remote.dto.datasource.club.response


data class ClubResponse(
    val type: String,
    val bannerUrl: String,
    val title: String,
    val description: String,
    val contact: String,
    val teacher: String,
    val isOpened: Boolean,
    val notionLink: String
)
