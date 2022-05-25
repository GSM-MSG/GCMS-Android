package com.msg.gcms.data.remote.dto.datasource.club.request

import com.msg.gcms.data.remote.dto.datasource.club.response.RelatedLink

data class CreateClubRequest(
    val q: String,
    val type: String,
    val title: String,
    val description: String,
    val contact: String,
    val relatedLink: RelatedLink?,
    val teacher: String?,
    val member: List<String>?,
    val activityUrls: List<String>,
    val bannerUrl: String
)
