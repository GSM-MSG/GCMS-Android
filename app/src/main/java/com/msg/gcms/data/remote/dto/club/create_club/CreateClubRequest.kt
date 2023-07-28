package com.msg.gcms.data.remote.dto.club.create_club

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.club.create_club.CreateClubData
import com.msg.gcms.domain.data.club.create_club.CreateClubData.OpeningApplication as domainOpeningApplication
import java.util.UUID

data class CreateClubRequest(
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("content")
    val description: String,
    @SerializedName("bannerImg")
    val bannerUrl:String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("notionLink")
    val notionLink: String,
    @SerializedName("teacher")
    val teacher: String?,
    @SerializedName("activityImgs")
    val activityUrls: List<String>?,
    @SerializedName("member")
    val member: List<UUID>?,
    @SerializedName("opening_application")
    val openingApplication: OpeningApplication
) {
    data class OpeningApplication(
        @SerializedName("field")
        val field: String,
        @SerializedName("subject")
        val subject: String,
        @SerializedName("reason")
        val reason: String,
        @SerializedName("target")
        val target: String,
        @SerializedName("effect")
        val effect: String
    )

    fun OpeningApplication.toClubData(): domainOpeningApplication {
        return domainOpeningApplication(
            field = field,
            subject = subject,
            reason = reason,
            target = target,
            effect = effect
        )
    }
}

fun CreateClubData.toRequest() = CreateClubRequest(
    type = type,
    title = title,
    description = description,
    bannerUrl = bannerUrl,
    contact = contact,
    notionLink = notionLink,
    teacher = teacher,
    activityUrls = activityUrls,
    member = member,
    openingApplication = openingApplication.toClubRequest()
)