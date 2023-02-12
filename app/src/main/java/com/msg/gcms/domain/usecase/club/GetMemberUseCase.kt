package com.msg.gcms.domain.usecase.club

import com.msg.gcms.domain.repository.ClubMemberRepository
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class GetMemberUseCase @Inject constructor(
    private val repository: ClubMemberRepository
) {
    suspend operator fun invoke(clubId: Long) = kotlin.runCatching {
        repository.getMemberList(clubId = clubId)
    }
}