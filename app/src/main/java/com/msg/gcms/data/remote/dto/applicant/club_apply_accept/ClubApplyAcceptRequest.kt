package com.msg.gcms.data.remote.dto.applicant.club_apply_accept

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ClubApplyAcceptRequest(
    @SerializedName("uuid")
    val uuid: UUID
)
