package com.msg.gcms.domain.data.applicant.get_applicant_list

import java.util.UUID

data class ApplicantListData(
    val uuid: UUID,
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val number: Int,
    val profileImg: String?
)