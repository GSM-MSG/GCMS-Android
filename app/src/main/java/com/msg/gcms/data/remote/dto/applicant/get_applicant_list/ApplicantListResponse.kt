package com.msg.gcms.data.remote.dto.applicant.get_applicant_list

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.applicant.get_applicant_list.ApplicantListData
import java.util.UUID

data class ApplicantListResponse(
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

fun ApplicantListResponse.toApplicantListData(): ApplicantListData {
    return ApplicantListData(
        uuid = uuid,
        email = email,
        name = name,
        grade = grade,
        classNum = classNum,
        number = number,
        profileImg = profileImg
    )
}