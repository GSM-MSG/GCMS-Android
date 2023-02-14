package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.club.create_club.CreateClubRequest
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class PostCreateClubUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(body: _root_ide_package_.com.msg.gcms.data.remote.dto.club.create_club.CreateClubRequest) = kotlin.runCatching {
        repository.postCreateClub(body = body)
    }
}