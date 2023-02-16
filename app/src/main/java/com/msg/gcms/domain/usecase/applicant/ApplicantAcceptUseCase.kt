package com.msg.gcms.domain.usecase.applicant

import com.msg.gcms.domain.data.applicant.clubApplyAccept.ClubApplyAcceptData
import com.msg.gcms.domain.repository.ApplicantRepository
import javax.inject.Inject

class ApplicantAcceptUseCase @Inject constructor(
    private val repository: ApplicantRepository
) {
    suspend operator fun invoke(clubId: Long, body: ClubApplyAcceptData) = kotlin.runCatching {
        repository.postApplicantAccept(clubId = clubId, body = body)
    }
}