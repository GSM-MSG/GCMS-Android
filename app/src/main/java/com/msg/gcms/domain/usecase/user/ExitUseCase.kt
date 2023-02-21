package com.msg.gcms.domain.usecase.user

import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ExitUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(clubId: Long) = kotlin.runCatching {
        repository.exitClub(clubId = clubId)
    }
}