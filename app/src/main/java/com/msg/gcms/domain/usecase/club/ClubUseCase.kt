package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.datasource.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.datasource.club.request.CreateClubRequest
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class ClubUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend fun postClubCancel(body: ClubIdentificationRequest) = repository.postClubCancel(body)

    suspend fun putClubOpen(body: ClubIdentificationRequest) = repository.putClubOpen(body)

    suspend fun putClubClose(body: ClubIdentificationRequest) = repository.putClubClose(body)

    suspend fun postCreateClub(body: CreateClubRequest) = repository.postCreateClub(body = body)
}