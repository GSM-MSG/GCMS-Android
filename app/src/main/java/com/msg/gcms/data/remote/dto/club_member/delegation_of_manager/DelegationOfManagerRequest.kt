package com.msg.gcms.data.remote.dto.club_member.delegation_of_manager

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class DelegationOfManagerRequest(
    @SerializedName("uuid")
    val uuid: UUID
)
