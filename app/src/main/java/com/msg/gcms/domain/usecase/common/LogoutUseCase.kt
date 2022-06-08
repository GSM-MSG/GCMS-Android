package com.msg.gcms.domain.usecase.common

import com.msg.gcms.domain.repository.CommonRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val commonRepository: CommonRepository
) {
    suspend fun postLogout() = commonRepository.postLogout()
}