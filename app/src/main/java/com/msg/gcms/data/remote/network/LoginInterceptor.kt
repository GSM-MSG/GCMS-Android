package com.msg.gcms.data.remote.network

import com.msg.gcms.base.di.GCMSApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class LoginInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val req =
            request().newBuilder().addHeader("Authorization", "Bearer ${GCMSApplication.prefs.accessToken}")
                .build()
        return proceed(req)
    }
}