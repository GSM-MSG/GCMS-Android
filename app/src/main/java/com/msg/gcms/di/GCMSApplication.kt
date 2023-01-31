package com.msg.gcms.di

import android.app.Application
import com.msg.gcms.data.local.dao.LoginToken
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GCMSApplication : Application() {
    companion object {
        lateinit var prefs: LoginToken
    }

    override fun onCreate() {
        super.onCreate()
        prefs = LoginToken(applicationContext)
    }
}
