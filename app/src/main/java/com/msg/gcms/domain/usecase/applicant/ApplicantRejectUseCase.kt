package com.msg.gcms.domain.usecase.applicant

import com.msg.gcms.domain.data.applicant.club_apply_reject.ClubApplyRejectData
import com.msg.gcms.domain.repository.ApplicantRepository
import javax.inject.Inject

class ApplicantRejectUseCase @Inject constructor(
    private val repository: ApplicantRepository
) {
    suspend operator fun invoke(clubId: Long, body: ClubApplyRejectData) = kotlin.runCatching {
        repository.postApplicantReject(clubId = clubId, body = body)
    }
}