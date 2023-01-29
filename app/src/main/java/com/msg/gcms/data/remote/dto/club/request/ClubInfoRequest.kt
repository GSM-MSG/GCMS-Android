package com.msg.gcms.data.remote.dto.club.request

data class ClubInfoRequest(
    val type : String,
    val title : String,
    val description : String,
    val bannerUrl : String,
    val contact : String,
    val notionLink: String,
    val teacher : String?,
    val activityUrls : List<String>?,
    val member : List<String>?
)