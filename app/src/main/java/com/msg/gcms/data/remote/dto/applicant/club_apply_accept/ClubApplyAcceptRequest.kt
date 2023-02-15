package com.msg.gcms.data.remote.dto.applicant.club_apply_accept

import com.google.gson.annotations.SerializedName

data class ClubApplyAcceptRequest(
    @SerializedName("uuid")
    val uuid: String
)
