package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.club.request.ClubIdentificationRequest
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class PutClubOpenUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(body: ClubIdentificationRequest) = kotlin.runCatching {
        repository.putClubOpen(body)
    }
}