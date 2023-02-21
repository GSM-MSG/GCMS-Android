package com.msg.gcms.domain.data.club.create_club

data class CreateClubData(
    val type: String,
    val title: String,
    val description: String,
    val bannerUrl:String,
    val contact: String,
    val notionLink: String,
    val teacher: String?,
    val activityUrls: List<String>?,
    val member: List<String>?,
)
