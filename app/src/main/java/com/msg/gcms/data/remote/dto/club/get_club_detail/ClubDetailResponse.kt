package com.msg.gcms.data.remote.dto.club.get_club_detail

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.club.get_club_detail.ClubDetailData

data class ClubDetailResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("type")
    val type: String,
    @SerializedName("bannerImg")
    val bannerImg: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("teacher")
    val teacher: String,
    @SerializedName("isOpened")
    val isOpened: Boolean,
    @SerializedName("notionLink")
    val notionLink: String,
    @SerializedName("activityImgs")
    val activityImgs: List<String>,
    @SerializedName("head")
    val head: ClubMemberResponse,
    @SerializedName("member")
    val member: List<ClubMemberResponse>,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("isApplied")
    val isApplied: Boolean
)

fun ClubDetailResponse.toClubDetailData(): ClubDetailData {
    return ClubDetailData(
        activityImgs = activityImgs,
        bannerImg = bannerImg,
        contact = contact,
        content = content,
        head = head.toClubMemberData(),
        id = id,
        isApplied = isApplied,
        isOpened = isOpened,
        member = member.map { it.toClubMemberData() },
        name = name,
        notionLink = notionLink,
        scope = scope,
        teacher = teacher,
        type = type
    )
}