package com.msg.gcms.domain.usecase.auth

import com.msg.gcms.domain.data.auth.SignInRequestData
import com.msg.gcms.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(body: SignInRequestData) = kotlin.runCatching {
        authRepository.postRegistration(body)
    }
}
