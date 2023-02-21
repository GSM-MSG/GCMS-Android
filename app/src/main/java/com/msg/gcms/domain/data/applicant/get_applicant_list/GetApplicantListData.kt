package com.msg.gcms.domain.data.applicant.get_applicant_list

data class GetApplicantListData(
    val userScope: String,
    val applicantList: List<ApplicantListData>
)
