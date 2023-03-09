package com.msg.gcms.domain.usecase.user

import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class GetProfileImageUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = kotlin.runCatching {
        repository.getProfileImage()
    }
}