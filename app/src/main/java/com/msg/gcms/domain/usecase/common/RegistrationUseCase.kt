package com.msg.gcms.domain.usecase.common

import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.domain.repository.CommonRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val repository: CommonRepository
) {
    suspend operator fun invoke(body: SignInRequest) = kotlin.runCatching {
        repository.postRegistration(body)
    }
}
