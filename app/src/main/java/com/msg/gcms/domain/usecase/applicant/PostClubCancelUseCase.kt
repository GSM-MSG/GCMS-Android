package com.msg.gcms.domain.usecase.applicant

import com.msg.gcms.domain.repository.ApplicantRepository
import javax.inject.Inject

class PostClubCancelUseCase @Inject constructor(
    private val repository: ApplicantRepository
) {
    suspend operator fun invoke(clubId: Long) = kotlin.runCatching {
        repository.deleteClubApply(clubId = clubId)
    }

}