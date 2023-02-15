package com.msg.gcms.data.remote.datasource.applicant

import com.msg.gcms.data.remote.dto.applicant.get_applicant_list.GetApplicantListResponse

interface ApplicantDataSource {
    suspend fun getApplicantList(clubId: Long): GetApplicantListResponse

    suspend fun postClubApply(clubId: Long)

    suspend fun deleteClubApply(clubId: Long)

    suspend fun postApplicantAccept(clubId: Long, body: )

    suspend fun postApplicantReject(clubId: Long, body: )
}