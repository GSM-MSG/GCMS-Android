package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.datasource.club.request.MemberManagementRequest
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ApplicantAcceptUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend fun accept(body: MemberManagementRequest) = repository.postApplicationAccept(body)
}