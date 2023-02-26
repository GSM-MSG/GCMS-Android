package com.msg.gcms.data.remote.dto.applicant.club_apply_reject

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ClubApplyRejectRequest(
    @SerializedName("uuid")
    val uuid: UUID
)
