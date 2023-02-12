package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.domain.repository.ApplicantRepository
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ApplicantAcceptUseCase @Inject constructor(
    private val repository: ApplicantRepository
) {
    suspend operator fun invoke(clubId: Long, body: MemberManagementRequest) = kotlin.runCatching {
        repository.postApplicantAccept(clubId = clubId, body = body)
    }
}