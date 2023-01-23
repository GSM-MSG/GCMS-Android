package com.msg.gcms.domain.usecase.club

import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class GetDetailUseCase @Inject constructor(
    private val repository : ClubRepository
) {
    suspend operator fun invoke(type: String, clubName: String) = repository.getDetail(type, clubName)
}