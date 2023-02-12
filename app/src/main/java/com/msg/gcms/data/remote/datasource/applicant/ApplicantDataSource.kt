package com.msg.gcms.data.remote.datasource.applicant

import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.response.MemberInfo

interface ApplicantDataSource {
    suspend fun getApplicantList(clubId: Long): MemberInfo

    suspend fun postClubApply(clubId: Long)

    suspend fun postApplicantAccept(clubId: Long, body: MemberManagementRequest)

    suspend fun postApplicantReject(clubId: Long, body: MemberManagementRequest)
}