package com.msg.gcms.domain.usecase.club_member

import com.msg.gcms.domain.data.club_member.delegation_of_manager.DelegationOfManagerData
import com.msg.gcms.domain.repository.ClubMemberRepository
import javax.inject.Inject

class MandateUseCase @Inject constructor(
    private val repository: ClubMemberRepository
) {
    suspend operator fun invoke(clubId: Long, body: DelegationOfManagerData) = kotlin.runCatching {
        repository.putDelegationOfRepresentation(clubId = clubId, body = body)
    }
}