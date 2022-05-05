package com.msg.gcms.domain.usecase.common

import com.msg.gcms.data.remote.dto.datasource.auth.request.LoginRequest
import com.msg.gcms.domain.repository.CommonRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: CommonRepository
) {
    suspend fun postLogin(body: LoginRequest) = repository.postLogin(body)
}
