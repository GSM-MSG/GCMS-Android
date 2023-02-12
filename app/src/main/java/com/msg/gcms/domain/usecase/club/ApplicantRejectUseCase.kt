package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.domain.repository.ApplicantRepository
import javax.inject.Inject

class ApplicantRejectUseCase @Inject constructor(
    private val repository: ApplicantRepository
) {
    suspend operator fun invoke(clubId: Long, body: MemberManagementRequest) = kotlin.runCatching {
        repository.postApplicantReject(clubId = clubId, body = body)
    }
}