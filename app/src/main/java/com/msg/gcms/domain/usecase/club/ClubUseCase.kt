package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.remote.dto.datasource.club.request.ClubIdentificationRequest
import com.msg.gcms.domain.repository.ClubRepository
import retrofit2.http.Body
import javax.inject.Inject

class ClubUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend fun getClubList(type: String) = repository.getClubList(type)

    suspend fun postClubApply(body: ClubIdentificationRequest) = repository.postClubApply(body)
}