package com.msg.gcms.domain.data.applicant.clubApplyAccept

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ClubApplyAcceptData(
    @SerializedName("uuid")
    val uuid: UUID
)
