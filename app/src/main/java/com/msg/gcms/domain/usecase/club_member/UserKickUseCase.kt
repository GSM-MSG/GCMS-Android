package com.msg.gcms.domain.usecase.club_member

import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.domain.repository.ClubMemberRepository
import javax.inject.Inject

class UserKickUseCase @Inject constructor(
    private val repository: ClubMemberRepository
) {
    suspend operator fun invoke(clubId: Long, body: MemberManagementRequest) = kotlin.runCatching {
        repository.deleteMemberExpel(clubId = clubId, body = body)
    }
}