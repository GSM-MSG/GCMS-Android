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

    override fun setAccessToken(accessToken: String) = setData(ACCESS_TOKEN, accessToken)

    override fun setRefreshToken(refreshToken: String) = setData(REFRESH_TOKEN, refreshToken)

    override fun setAccessExpiredAt(accessExp: String) = setData(ACCESS_EXP, accessExp)

    override fun setRefreshExpiredAt(refreshExp: String) = setData(REFRESH_EXP, refreshExp)

    override fun getAccessToken(): String {
        return getSharedPreferences().getString(ACCESS_TOKEN, "") ?: ""
    }

    override fun getRefreshToken(): String {
        return getSharedPreferences().getString(REFRESH_TOKEN, "") ?: ""
    }

    override fun getAccessExpiredAt(): String {
        return getSharedPreferences().getString(ACCESS_EXP, "") ?: ""
    }

    override fun getRefreshExpiredAt(): String {
        return getSharedPreferences().getString(REFRESH_EXP, "") ?: ""
    }

    private fun setData(id: String, data: String?) =
        getSharedPreferences().edit().let {
            it.putString(id, data)
            it.apply()
        }

    private fun getSharedPreferences() =
        context.getSharedPreferences(NAME, MODE_PRIVATE)
}