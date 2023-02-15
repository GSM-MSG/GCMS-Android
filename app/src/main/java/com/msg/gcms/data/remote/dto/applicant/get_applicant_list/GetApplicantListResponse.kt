package com.msg.gcms.data.remote.dto.applicant.get_applicant_list

import com.google.gson.annotations.SerializedName

data class GetApplicantListResponse(
    @SerializedName("userScope")
    val userScope: String,
    @SerializedName("applicantList")
    val applicantList: ApplicantListResponse
)
