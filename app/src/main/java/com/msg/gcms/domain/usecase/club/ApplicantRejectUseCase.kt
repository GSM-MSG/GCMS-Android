package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.datasource.club.request.MemberManagementRequest
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ApplicantRejectUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend fun reject(body: MemberManagementRequest) = repository.postApplicationReject(body)
}