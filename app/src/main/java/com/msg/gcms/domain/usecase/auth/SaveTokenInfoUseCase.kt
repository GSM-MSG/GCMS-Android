package com.msg.gcms.domain.usecase.auth

import com.msg.gcms.domain.repository.AuthRepository
import javax.inject.Inject

class SaveTokenInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String, accessExp: String, refreshExp: String) =
        kotlin.runCatching {
            authRepository.saveTokenInfo(accessToken, refreshToken, accessExp, refreshExp)
        }
}