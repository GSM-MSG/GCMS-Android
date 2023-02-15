package com.msg.gcms.domain.repository

import com.msg.gcms.domain.data.applicant.clubApplyAccept.ClubApplyAcceptData
import com.msg.gcms.domain.data.applicant.club_apply_reject.ClubApplyRejectData
import com.msg.gcms.domain.data.applicant.get_applicant_list.GetApplicantListData

interface ApplicantRepository {
    suspend fun getApplicantList(clubId: Long): GetApplicantListData

    suspend fun postClubApply(clubId: Long)

    suspend fun postApplicantAccept(clubId: Long, body: ClubApplyAcceptData)

    suspend fun postApplicantReject(clubId: Long, body: ClubApplyRejectData)

    suspend fun deleteClubApply(clubId: Long)


}