package com.msg.gcms.data.remote.network

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.msg.gcms.BuildConfig
import com.msg.gcms.data.local.datastorage.AuthDataStorage
import com.msg.gcms.domain.exception.NeedLoginException
import com.msg.gcms.util.removeDot
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
        val ignorePath = listOf(
            "/auth",
            "/club"
        )
        val ignoreMethod = listOf(
            "POST",
            "GET"
        )

        ignorePath.forEachIndexed { index, s ->
            if (s == path && ignoreMethod[index] == method) {
                return chain.proceed(request)
            }
        }

        val currentTime = LocalDateTime.now()
        val accessExp = LocalDateTime.parse(
            authDataStorage.getAccessExpiredAt().substring(0, 19)
        )
        val refreshExp = LocalDateTime.parse(
            authDataStorage.getRefreshExpiredAt().substring(0, 19)
        )
        val refreshToken = authDataStorage.getRefreshToken()

        if (currentTime.isAfter(refreshExp)) throw NeedLoginException()

        if (currentTime.isAfter(accessExp)) {
            val client = OkHttpClient()
            val refreshRequest = Request.Builder()
                .url(BuildConfig.BASE_URL + "auth")
                .patch("".toRequestBody("application/json".toMediaTypeOrNull()))
                .addHeader(
                    "Refresh-Token",
                    "Bearer $refreshToken"
                )
                .build()
            val jsonParser = JsonParser()
            val response = client.newCall(refreshRequest).execute()
            if (response.isSuccessful) {
                val token = jsonParser.parse(response.body!!.string()) as JsonObject
                authDataStorage.setAccessToken(token["accessToken"].toString().removeDot())
                authDataStorage.setRefreshToken(token["refreshToken"].toString().removeDot())
                authDataStorage.setAccessExpiredAt(token["accessExp"].toString().removeDot())
                authDataStorage.setRefreshExpiredAt(token["refreshExp"].toString().removeDot())
            } else throw NeedLoginException()
        }

        val accessToken = authDataStorage.getAccessToken()

        return proceed(
            request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        )
    }
}