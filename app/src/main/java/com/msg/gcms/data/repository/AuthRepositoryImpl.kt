package com.msg.gcms.data.repository

import com.msg.gcms.data.local.datasource.LocalDataSource
import com.msg.gcms.data.mapper.AuthMapper
import com.msg.gcms.data.remote.datasource.auth.AuthDataSource
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.domain.data.auth.SignInRequestData
import com.msg.gcms.domain.data.auth.SignInResponseData
import com.msg.gcms.domain.repository.AuthRepository
import java.time.LocalDateTime
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDatasource: AuthDataSource,
    private val localDataSource: LocalDataSource
) : AuthRepository {
    override suspend fun postRegistration(body: SignInRequestData): SignInResponseData {
        return AuthMapper.mapperToSignInData(
            remoteDatasource.postRegistration(
                body = SignInRequest(
                    code = body.code
                )
            )
        )
    }

    override suspend fun logout() =
        remoteDatasource.logout()

    override suspend fun checkLoginStatus(): Boolean =
        if (localDataSource.getRefreshExp().isBlank()) {
            false
        } else {
            val currentTime = LocalDateTime.now()
            val refreshExpriedAt = LocalDateTime.parse(
                localDataSource.getRefreshExp()
            )
            !currentTime.isAfter(refreshExpriedAt)
        }

    override suspend fun saveTokenInfo(
        accessToken: String,
        refreshToken: String,
        accessExp: String,
        refreshExp: String
    ) =
        localDataSource.saveTokenInfo(accessToken, refreshToken, accessExp, refreshExp)
}
