package com.msg.gcms.data.local.datastorage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthDataStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AuthDataStorage {

    companion object {
        const val NAME = "TokenInfo"
        const val ACCESS_TOKEN = "accessToken"
        const val REFRESH_TOKEN = "refreshToken"
        const val ACCESS_EXP = "accessExp"
        const val REFRESH_EXP = "refreshExp"
    }

    override fun setAccessToken(token: String) {
        getSharedPreferences().edit().putString(ACCESS_TOKEN, token).apply()
    }

    override fun getAccessToken(): String {
        return getSharedPreferences().getString(ACCESS_TOKEN, "") ?: ""
    }

    override fun setRefreshToken(token: String) {
        getSharedPreferences().edit().putString(REFRESH_TOKEN, token).apply()
    }

    override fun getRefreshToken(): String {
        return getSharedPreferences().getString(REFRESH_TOKEN, "") ?: ""
    }

    override fun setAccessExpiredAt(accessExp: String) {
        getSharedPreferences().edit().putString(ACCESS_EXP, accessExp).apply()
    }

    override fun getAccessExpiredAt(): String {
        return getSharedPreferences().getString(ACCESS_EXP, "") ?: ""
    }

    override fun setRefreshExpiredAt(refreshExp: String) {
        getSharedPreferences().edit().putString(ACCESS_EXP, refreshExp).apply()
    }

    override fun getRefreshExpiredAt(): String {
        return getSharedPreferences().getString(REFRESH_EXP, "") ?: ""
    }

    private fun getSharedPreferences() =
        context.getSharedPreferences(NAME, MODE_PRIVATE)
}