package com.msg.gcms.data.remote.network

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageAPI {
    @Multipart
    @POST("image")
    suspend fun postImage(
        @Part files: List<MultipartBody.Part>
    ): List<String>
}