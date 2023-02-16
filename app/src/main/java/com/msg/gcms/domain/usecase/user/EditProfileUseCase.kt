package com.msg.gcms.domain.usecase.user

import com.msg.gcms.data.remote.dto.user.modify_profile_image.ModifyProfileImageRequest
import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(img: ModifyProfileImageRequest) = kotlin.runCatching {
        repository.putProfile(img)
    }
}