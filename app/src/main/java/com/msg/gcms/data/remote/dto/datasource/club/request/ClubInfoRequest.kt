package com.msg.gcms.data.remote.dto.datasource.club.request

import com.msg.gcms.data.remote.dto.datasource.club.response.RelatedLink

data class ClubInfoRequest(
    val type : String,
    val title : String,
    val description : String,
    val bannerUrl : String,
    val contact : String,
    val relatedLink: RelatedLink?,
    val teacher : String?,
    val activityUrls : List<String>?,
    val member : List<String>?
)