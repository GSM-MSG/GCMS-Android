package com.msg.gcms.data.remote.dto.datasource.club.request

import com.msg.gcms.data.remote.dto.datasource.club.response.RelatedLink

data class ModifyClubInfoRequest(
    val q : String,
    val type : String,
    val title : String,
    val description : String,
    val contact : String,
    val relatedLink: RelatedLink?,
    val teacher :String?,
    val deleteActivityUrls: List<String>?,
    val deleteMember : List<String>?,
    val newMember : List<String>?,
    val bannerUrl: String,
    val activityUrls: List<String>?
)
