package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.datasource.club.request.CreateClubRequest
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ClubUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend fun postCreateClub(body: CreateClubRequest) = repository.postCreateClub(body = body)
}