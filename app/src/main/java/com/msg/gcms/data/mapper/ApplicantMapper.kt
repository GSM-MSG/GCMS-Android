package com.msg.gcms.data.mapper

import com.msg.gcms.data.remote.dto.applicant.get_applicant_list.ApplicantListResponse
import com.msg.gcms.data.remote.dto.applicant.get_applicant_list.GetApplicantListResponse
import com.msg.gcms.domain.data.applicant.get_applicant_list.ApplicantListData
import com.msg.gcms.domain.data.applicant.get_applicant_list.GetApplicantListData

object ApplicantMapper {

    fun mapperToGetApplicantListData(data: GetApplicantListResponse): GetApplicantListData {
        return GetApplicantListData(
            applicantList = data.applicantList.map { mapperToApplicantListData(it) },
            userScope = data.userScope
        )
    }

    private fun mapperToApplicantListData(data: ApplicantListResponse): ApplicantListData {
        return ApplicantListData(
            classNum = data.classNum,
            email = data.email,
            grade = data.grade,
            name = data.name,
            number = data.number,
            profileImg = data.profileImg,
            uuid = data.uuid
        )
    }
}