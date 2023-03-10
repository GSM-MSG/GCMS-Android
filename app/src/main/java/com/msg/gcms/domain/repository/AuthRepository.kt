package com.msg.gcms.domain.repository

import com.msg.gcms.domain.data.auth.SignInRequestData
import com.msg.gcms.domain.data.auth.SignInResponseData

interface AuthRepository {
    suspend fun postRegistration(
        body: SignInRequestData
    ): SignInResponseData

    suspend fun logout()

    suspend fun checkLoginStatus(): Boolean

    suspend fun saveTokenInfo(
        accessToken: String,
        refreshToken: String,
        accessExp: String,
        refreshExp: String,
        fcmToken: String
    )
}
