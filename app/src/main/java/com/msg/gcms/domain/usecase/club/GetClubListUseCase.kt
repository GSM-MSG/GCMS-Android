package com.msg.gcms.domain.usecase.club

import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class GetClubListUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(type: String) = kotlin.runCatching {
        repository.getClubList(type)
    }
}