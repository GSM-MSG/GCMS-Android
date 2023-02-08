package com.msg.gcms.data.local.datasource

import com.msg.gcms.data.local.datastorage.AuthDataStorage
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val authDataStorage: AuthDataStorage
) : LocalDataSource {
    override suspend fun getAccessToken(): String = authDataStorage.getAccessToken()

    override suspend fun getRefreshToken(): String = authDataStorage.getRefreshToken()

    override suspend fun getAccessExp(): String = authDataStorage.getAccessExpiredAt()

    override suspend fun getRefreshExp(): String = authDataStorage.getRefreshExpiredAt()

    override suspend fun saveTokenInfo(
        accessToken: String, refreshToken: String, accessExp: String, refreshExp: String
    ) {
        with(authDataStorage) {
            setAccessToken(accessToken)
            setRefreshToken(refreshToken)
            setAccessExpiredAt(accessExp)
            setRefreshExpiredAt(refreshExp)
        }
    }
}