package com.msg.gcms.domain.usecase.auth

import com.msg.gcms.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val commonRepository: AuthRepository
){
    suspend operator fun invoke() = kotlin.runCatching {
        commonRepository.postLogout()
    }
}