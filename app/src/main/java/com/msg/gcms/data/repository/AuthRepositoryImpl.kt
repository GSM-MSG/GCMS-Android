package com.msg.gcms.data.repository

import com.msg.gcms.data.local.datasource.LocalDataSource
import com.msg.gcms.data.mapper.AuthMapper
import com.msg.gcms.data.remote.datasource.auth.AuthDataSource
import com.msg.gcms.data.remote.dto.auth.request.RefreshRequest
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.domain.data.auth.SignInRequestData
import com.msg.gcms.domain.data.auth.SignInResponseData
import com.msg.gcms.domain.exception.NeedLoginException
import com.msg.gcms.domain.repository.AuthRepository
import java.time.LocalDateTime
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDatasource: AuthDataSource,
    private val localDataSource: LocalDataSource
) : AuthRepository {
    override suspend fun postRegistration(body: SignInRequestData): SignInResponseData =
        AuthMapper.mapperToSignInData(
            remoteDatasource.postRegistration(
                body = SignInRequest(
                    code = body.code,
                    token = body.token
                )
            )
        )

    override suspend fun logout() =
        remoteDatasource.logout()

    override suspend fun checkLoginStatus() {
        if (localDataSource.getRefreshExp().isBlank()) throw NeedLoginException()

        val accessExpiredAt = LocalDateTime.parse(
            localDataSource.getAccessExp()
        )
        val refreshToken = localDataSource.getRefreshToken()
        val fcmToken = localDataSource.getFcmToken()
        if (LocalDateTime.now().isAfter(accessExpiredAt)) return

        val response = remoteDatasource.refreshToken(
            header = "Bearer $refreshToken",
            body = RefreshRequest(fcmToken)
        )

        localDataSource.saveTokenInfo(
            accessToken = response.accessToken,
            refreshToken = response.refreshToken,
            accessExp = response.accessExp,
            refreshExp = response.refreshExp,
            fcmToken = fcmToken
        )
    }

    override suspend fun saveTokenInfo(
        accessToken: String,
        refreshToken: String,
        accessExp: String,
        refreshExp: String,
        fcmToken: String
    ) =
        localDataSource.saveTokenInfo(accessToken, refreshToken, accessExp, refreshExp, fcmToken)
}
