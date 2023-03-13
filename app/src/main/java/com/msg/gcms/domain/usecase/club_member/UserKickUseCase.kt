package com.msg.gcms.domain.usecase.club_member

import com.msg.gcms.domain.data.club_member.member_expelled.MemberExpelledData
import com.msg.gcms.domain.repository.ClubMemberRepository
import javax.inject.Inject

class UserKickUseCase @Inject constructor(
    private val repository: ClubMemberRepository
) {
    suspend operator fun invoke(clubId: Long, body: MemberExpelledData) = kotlin.runCatching {
        repository.deleteMemberExpel(clubId = clubId, body = body)
    }
}