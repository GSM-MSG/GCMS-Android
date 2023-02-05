package com.msg.gcms.data.remote.network

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.msg.gcms.BuildConfig
import com.msg.gcms.di.GCMSApplication
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.time.LocalDateTime

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

        val currentTime = LocalDateTime.now()
        val accessExp = LocalDateTime.parse(
            GCMSApplication.prefs.accessExp!!.substring(0, 19)
        )

        if (currentTime.isAfter(accessExp)) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BuildConfig.BASE_URL + "")
                .patch("".toRequestBody("application/json".toMediaTypeOrNull()))
                .addHeader("RefreshToken", GCMSApplication.prefs.refreshToken!!)
                .build()
            val jsonParser = JsonParser()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val token = jsonParser.parse(response.body!!.string()) as JsonObject
                GCMSApplication.prefs.accessToken = token["accessToken"].toString().removeQuotation()
                GCMSApplication.prefs.refreshToken = token["refreshToken"].toString().removeQuotation()
                GCMSApplication.prefs.accessExp = token["accessExp"].toString().removeQuotation()
                GCMSApplication.prefs.refreshExp = token["refreshExp"].toString().removeQuotation()
            } else throw RuntimeException()
        }

        return proceed(
            request.newBuilder()
                .addHeader("Authorization", "Bearer ${GCMSApplication.prefs.accessToken}")
                .build()
        )
    }

    private fun String.removeQuotation(): String {
        return this.replace("\"","")
    }
}