package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.club.request.ModifyClubInfoRequest
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class EditClubInfoUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(body: ModifyClubInfoRequest) = kotlin.runCatching {
        repository.putChangeClub(body)
    }
}