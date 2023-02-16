package com.msg.gcms.domain.usecase.user

import com.msg.gcms.domain.data.user.modify_profile_image.ModifyProfileImageData
import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(img: ModifyProfileImageData) = kotlin.runCatching {
        repository.putProfile(img)
    }
}