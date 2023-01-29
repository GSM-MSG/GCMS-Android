package com.msg.gcms.domain.usecase.user

import com.msg.gcms.data.remote.dto.user.request.UserProfileRequest
import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(img: UserProfileRequest) = kotlin.runCatching {
        repository.putProfile(img)
    }
}