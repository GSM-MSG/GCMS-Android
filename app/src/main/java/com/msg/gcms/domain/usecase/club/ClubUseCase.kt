package com.msg.gcms.domain.usecase.club

import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ClubUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend fun getClubList(type: String) = repository.getClubList(type)
}