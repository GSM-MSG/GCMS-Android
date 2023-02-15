package com.msg.gcms.domain.usecase.club

import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class PostCreateClubUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(body: _root_ide_package_.com.msg.gcms.domain.data.club.create_club.CreateClubData) = kotlin.runCatching {
        repository.postCreateClub(body = body)
    }
}