package com.msg.gcms.domain.usecase.club

import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ClubDeleteUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(clubId: Long) = kotlin.runCatching {
        repository.deleteClub(clubId)
    }
}