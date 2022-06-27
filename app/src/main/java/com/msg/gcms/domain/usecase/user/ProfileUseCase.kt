package com.msg.gcms.domain.usecase.user

import com.msg.gcms.domain.repository.CommonRepository
import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun getUserInfo() = userRepository.getUserInfo()

}