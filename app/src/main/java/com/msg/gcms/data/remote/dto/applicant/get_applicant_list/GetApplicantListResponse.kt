package com.msg.gcms.data.remote.dto.applicant.get_applicant_list

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.applicant.get_applicant_list.GetApplicantListData

data class GetApplicantListResponse(
    @SerializedName("scope")
    val userScope: String,
    @SerializedName("applicantList")
    val applicantList: List<ApplicantListResponse>
)

fun GetApplicantListResponse.toApplicantListData(): GetApplicantListData {
    return GetApplicantListData(
        applicantList = applicantList.map { it.toApplicantListData() },
        userScope = userScope
    )
}