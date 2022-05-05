package com.msg.gcms.base.di

import android.app.Application
import com.msg.gcms.data.local.dao.LoginAccessToken
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GCMSApplication : Application() {
    companion object{
        lateinit var prefs: LoginAccessToken
    }

    override fun onCreate() {
        super.onCreate()
        prefs = LoginAccessToken(applicationContext)
    }
}