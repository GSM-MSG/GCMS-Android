package com.msg.gcms.domain.usecase.user

import com.msg.gcms.data.remote.dto.datasource.user.request.UserDeleteRequest
import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class ExitUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun postExit(body: UserDeleteRequest) = repository.postExit(body)
}