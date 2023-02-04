package com.msg.gcms.data.local.dao

import android.content.Context
import android.content.Context.MODE_PRIVATE

class TokenHandler(context: Context) {
    private val name = "TokenInfo"

    private val prefs = context.getSharedPreferences(name, MODE_PRIVATE)

    var accessToken: String?
        get() = prefs.getString("accessToken", null)
        set(value) {
            prefs.edit().putString("accessToken", value).apply()
        }

    var refreshToken: String?
        get() = prefs.getString("refreshToken", null)
        set(value) {
            prefs.edit().putString("refreshToken", value).apply()
        }

    var accessExp: String?
        get() = prefs.getString("accessExp", null)
        set(value) {
            prefs.edit().putString("accessExp", value).apply()
        }

    var refreshExp: String?
        get() = prefs.getString("refreshExp", null)
        set(value) {
            prefs.edit().putString("refreshExp", value).apply()
        }
}
