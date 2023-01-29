package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ApplicantRejectUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(body: MemberManagementRequest) = kotlin.runCatching {
        repository.postApplicationReject(body)
    }
}