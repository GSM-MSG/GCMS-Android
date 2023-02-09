package com.msg.gcms.data.remote.network

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.msg.gcms.BuildConfig
import com.msg.gcms.data.local.datastorage.AuthDataStorage
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

class LoginInterceptor @Inject constructor(
    private val authDataStorage: AuthDataStorage
) : Interceptor {
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
            authDataStorage.getRefreshExpiredAt().substring(0, 19)
        )

        if (currentTime.isAfter(accessExp)) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BuildConfig.BASE_URL + "auth")
                .patch("".toRequestBody("application/json".toMediaTypeOrNull()))
                .addHeader(
                    "RefreshToken",
                    "Bearer ${authDataStorage.getRefreshExpiredAt()}"
                )
                .build()
            val jsonParser = JsonParser()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val token = jsonParser.parse(response.body!!.string()) as JsonObject
                authDataStorage.setAccessToken(token["accessToken"].toString())
                authDataStorage.setRefreshToken(token["refreshToken"].toString())
                authDataStorage.setAccessExpiredAt(token["accessExp"].toString())
                authDataStorage.setRefreshExpiredAt(token["refreshExp"].toString())
            } else throw RuntimeException()
        }

        return proceed(
            request.newBuilder()
                .addHeader("Authorization", "Bearer ${authDataStorage.getAccessToken()}")
                .build()
        )
    }
}