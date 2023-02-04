package com.msg.gcms.data.remote.network

import com.msg.gcms.di.GCMSApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class LoginInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val request = chain.request()
        val path = request.url.encodedPath
        val method = request.method
        val ignorePath = "/auth"
        val ignoreMethod = "POST"

        if (ignorePath == path && ignoreMethod == method) {
            return chain.proceed(request)
        }

        val accessTokenRequest =
            request.newBuilder()
                .addHeader("Authorization", "Bearer ${GCMSApplication.prefs.accessToken}")
                .build()

        return proceed(accessTokenRequest)
    }
}