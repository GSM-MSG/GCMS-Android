package com.msg.gcms.domain.repository

import com.msg.gcms.domain.data.applicant.get_applicant_list.GetApplicantListData

interface ApplicantRepository {
    suspend fun getApplicantList(clubId: Long): GetApplicantListData

    suspend fun postClubApply(clubId: Long)

    suspend fun postApplicantAccept(clubId: Long, body: MemberManagementRequest)

    suspend fun postApplicantReject(clubId: Long, body: MemberManagementRequest)

    suspend fun deleteClubApply(clubId: Long)


}