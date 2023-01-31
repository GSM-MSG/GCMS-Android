package com.msg.gcms.data.remote.dto.club.request

data class CreateClubRequest(
    val type: String,
    val title: String,
    val description: String,
    val contact: String,
    val notionLink: String,
    val teacher: String?,
    val member: List<String>?,
    val activityUrls: List<String>?,
    val bannerUrl:String
)
