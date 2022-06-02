package com.msg.gcms.data.local.dao

import android.content.Context
import android.content.Context.MODE_PRIVATE

class LoginToken(private val context: Context) {
    private val name = "loginToken"

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
}
