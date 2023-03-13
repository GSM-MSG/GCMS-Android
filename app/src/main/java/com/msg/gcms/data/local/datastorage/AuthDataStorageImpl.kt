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
        const val FCM_TOKEN = "fcmToken"
    }

    override fun setAccessToken(accessToken: String) = setData(ACCESS_TOKEN, accessToken)

    override fun setRefreshToken(refreshToken: String) = setData(REFRESH_TOKEN, refreshToken)

    override fun setAccessExpiredAt(accessExp: String) = setData(ACCESS_EXP, accessExp)

    override fun setRefreshExpiredAt(refreshExp: String) = setData(REFRESH_EXP, refreshExp)

    override fun setFcmToken(fcmToken: String) = setData(FCM_TOKEN, fcmToken)

    override fun getAccessToken(): String =
        getSharedPreferences().getString(ACCESS_TOKEN, "") ?: ""

    override fun getRefreshToken(): String =
        getSharedPreferences().getString(REFRESH_TOKEN, "") ?: ""

    override fun getAccessExpiredAt(): String =
        getSharedPreferences().getString(ACCESS_EXP, "") ?: ""

    override fun getRefreshExpiredAt(): String =
        getSharedPreferences().getString(REFRESH_EXP, "") ?: ""

    override fun getFcmToken(): String =
        getSharedPreferences().getString(FCM_TOKEN, "") ?: ""

    private fun setData(id: String, data: String?) =
        getSharedPreferences().edit().let {
            it.putString(id, data)
            it.apply()
        }

    private fun getSharedPreferences() =
        context.getSharedPreferences(NAME, MODE_PRIVATE)
}