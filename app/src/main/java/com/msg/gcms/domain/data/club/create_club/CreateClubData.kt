package com.msg.gcms.domain.data.club.create_club

import com.msg.gcms.data.remote.dto.club.create_club.CreateClubRequest.OpeningApplication as dataOpeningApplication
import java.util.UUID

data class CreateClubData(
    val type: String,
    val title: String,
    val description: String,
    val bannerUrl:String,
    val contact: String,
    val notionLink: String,
    val teacher: String?,
    val activityUrls: List<String>?,
    val member: List<UUID>?,
    val openingApplication: OpeningApplication
) {
    data class OpeningApplication(
        val field: String,
        val subject: String,
        val reason: String,
        val target: String,
        val effect: String
    )

    fun OpeningApplication.toClubRequest(): dataOpeningApplication {
        return dataOpeningApplication(
            field = field,
            subject = subject,
            reason = reason,
            target = target,
            effect = effect
        )
    }
}