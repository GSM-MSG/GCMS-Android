package com.msg.gcms.data.remote.network

import com.msg.gcms.di.GCMSApplication
import okhttp3.Interceptor
import okhttp3.Response

class loginInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req =
            chain.request().newBuilder().addHeader("Authorization", GCMSApplication.prefs.token ?: "").build()
        return chain.proceed(req)
    }
}