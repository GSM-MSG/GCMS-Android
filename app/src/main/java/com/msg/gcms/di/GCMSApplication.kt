package com.msg.gcms.di

import android.app.Application
import com.msg.gcms.data.local.dao.TokenHandler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GCMSApplication : Application() {
    companion object {
        lateinit var prefs: TokenHandler
    }

    override fun onCreate() {
        super.onCreate()
        prefs = TokenHandler(applicationContext)
    }
}
