package com.msg.gcms.domain.usecase.user

import com.msg.gcms.data.remote.dto.datasource.user.request.UserProfileRequest
import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun putProfile(img: UserProfileRequest) = repository.putProfile(img)
}