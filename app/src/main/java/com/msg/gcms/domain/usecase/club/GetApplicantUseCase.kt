package com.msg.gcms.domain.usecase.club

import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class GetApplicantUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend fun getApplicant(clubName: String, type: String) = repository.getApplicationList(clubName, type)
}