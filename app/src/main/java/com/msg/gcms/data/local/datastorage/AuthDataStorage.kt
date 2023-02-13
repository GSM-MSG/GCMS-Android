package com.msg.gcms.data.local.datastorage

interface AuthDataStorage {
    fun setAccessToken(accessToken: String)

    fun getAccessToken(): String

    fun setRefreshToken(refreshToken: String)

    fun getRefreshToken(): String

    fun setAccessExpiredAt(accessExp: String)

    fun getAccessExpiredAt(): String

    fun setRefreshExpiredAt(refreshExp: String)

    fun getRefreshExpiredAt(): String
}