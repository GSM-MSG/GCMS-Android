package com.msg.gcms.domain.usecase.club

import com.msg.gcms.domain.data.club.modify_club_info.ModifyClubInfoData
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class EditClubInfoUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(body: ModifyClubInfoData, clubId: Long) = kotlin.runCatching {
        repository.putChangeClub(body, clubId)
    }
}