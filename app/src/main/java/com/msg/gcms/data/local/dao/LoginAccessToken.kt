package com.msg.gcms.data.local.dao

import android.content.Context
import android.content.Context.MODE_PRIVATE

class LoginAccessToken(private val context: Context) {
    private val name = "accessToken"

    private val prefs = context.getSharedPreferences(name, MODE_PRIVATE)

    var token: String?
    get() = prefs.getString("token", null)
    set(value){
        prefs.edit().putString("token", value).apply()
    }
}