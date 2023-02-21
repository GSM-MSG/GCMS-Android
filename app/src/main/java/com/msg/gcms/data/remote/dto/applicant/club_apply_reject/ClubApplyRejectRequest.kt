package com.msg.gcms.data.remote.dto.applicant.club_apply_reject

import com.google.gson.annotations.SerializedName

data class ClubApplyRejectRequest(
    @SerializedName("uuid")
    val uuid: String
)
