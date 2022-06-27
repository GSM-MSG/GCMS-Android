package com.msg.gcms.domain.usecase.user

import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun deleteUser() = repository.deleteUser()
}