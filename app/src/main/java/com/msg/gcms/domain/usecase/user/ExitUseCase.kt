package com.msg.gcms.domain.usecase.user

import com.msg.gcms.data.remote.dto.user.request.UserDeleteRequest
import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class ExitUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(body: UserDeleteRequest) = kotlin.runCatching {
        repository.postExit(body)
    }
}