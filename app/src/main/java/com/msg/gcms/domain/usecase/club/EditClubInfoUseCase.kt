package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.club.modify_club_info.ModifyClubInfoRequest
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class EditClubInfoUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(body: ModifyClubInfoRequest, clubId: Long) = kotlin.runCatching {
        repository.putChangeClub(body, clubId)
    }
}