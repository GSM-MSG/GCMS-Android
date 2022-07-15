package com.msg.gcms.domain.usecase.club

import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class GetMemberUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend fun getMember(clubName: String, type: String) = repository.getMemberList(clubName, type)
}