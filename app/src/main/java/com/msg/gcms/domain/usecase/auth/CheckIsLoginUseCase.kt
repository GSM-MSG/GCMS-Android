package com.msg.gcms.domain.usecase.auth

import com.msg.gcms.domain.repository.AuthRepository
import javax.inject.Inject

class CheckIsLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = kotlin.runCatching {
        authRepository.checkIsLogin()
    }
}