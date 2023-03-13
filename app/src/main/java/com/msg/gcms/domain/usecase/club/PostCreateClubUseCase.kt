package com.msg.gcms.domain.usecase.club

import com.msg.gcms.domain.data.club.create_club.CreateClubData
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class PostCreateClubUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(body: CreateClubData) = kotlin.runCatching {
        repository.postCreateClub(body = body)
    }
}