package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.club.request.ClubIdentificationRequest
import com.msg.gcms.domain.repository.ApplicantRepository
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class PostClubApplyUseCase @Inject constructor(
    private val repository: ApplicantRepository
) {
    suspend operator fun invoke(clubId: Long) = kotlin.runCatching {
        repository.postClubApply(clubId = clubId)
    }

}