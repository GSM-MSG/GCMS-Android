package com.msg.gcms.domain.usecase.common

import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.domain.repository.CommonRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val repository: CommonRepository
) {
    suspend fun postRegistration(body: RegisterRequest) = repository.postRegistration(body)
}
