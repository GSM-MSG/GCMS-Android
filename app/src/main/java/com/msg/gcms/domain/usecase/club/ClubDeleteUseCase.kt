package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.datasource.club.request.ClubIdentificationRequest
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ClubDeleteUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(body: ClubIdentificationRequest) = kotlin.runCatching {
        repository.deleteClub(body)
    }
}