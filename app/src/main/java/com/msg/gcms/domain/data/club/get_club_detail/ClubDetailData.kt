package com.msg.gcms.domain.data.club.get_club_detail

data class ClubDetailData(
    val id: Long,
    val type: String,
    val bannerImg: String,
    val name: String,
    val content: String,
    val contact: String,
    val teacher: String,
    val isOpened: Boolean,
    val notionLink: String,
    val activityImgs: List<String>,
    val head: ClubMemberData,
    val member: List<ClubMemberData>,
    val scope: String,
    val isApplied: Boolean
)
