package com.msg.gcms.domain.usecase.club

import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class PutClubCloseUseCase @Inject constructor(
    private val repository: ClubRepository
){
    suspend operator fun invoke(clubId: Long) = kotlin.runCatching {
        repository.putClubClose(clubId = clubId)
    }
}