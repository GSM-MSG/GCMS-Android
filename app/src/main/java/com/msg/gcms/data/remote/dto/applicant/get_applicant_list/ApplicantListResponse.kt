package com.msg.gcms.data.remote.dto.applicant.get_applicant_list

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ApplicantListResponse (
    @SerializedName("uuid")
    val uuid: UUID,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("grade")
    val grade: Int,
    @SerializedName("classNum")
    val classNum: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("profileImg")
    val profileImg: String?
)