package com.msg.gcms.domain.usecase.auth

import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(body: SignInRequest) = kotlin.runCatching {
        repository.postRegistration(body)
    }
}