package com.msg.gcms.data.repository

import com.msg.gcms.data.local.datasource.LocalDataSource
import com.msg.gcms.data.remote.datasource.AuthDataSource
import com.msg.gcms.data.remote.dto.auth.request.SignInRequest
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse
import com.msg.gcms.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDatasource: AuthDataSource,
    private val localDataSource: LocalDataSource
) : AuthRepository {
    override suspend fun postRegistration(body: SignInRequest): SignInResponse =
        remoteDatasource.postRegistration(body = body)

    override suspend fun postLogout() = remoteDatasource.postLogout()

    override suspend fun postRefresh(): SignInResponse = remoteDatasource.postRefresh()

    override suspend fun getAccessToken(): String = localDataSource.getAccessToken()

    override suspend fun getRefreshToken(): String = localDataSource.getRefreshToken()

    override suspend fun getAccessExp(): String = localDataSource.getAccessExp()

    override suspend fun getRefreshExp(): String = localDataSource.getRefreshExp()

    override suspend fun saveTokenInfo(accessToken: String, refreshToken: String, accessExp: String, refreshExp: String) =
        localDataSource.saveTokenInfo(accessToken, refreshToken, accessExp, refreshExp)
}
