package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.image.ImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageAPI {
    @Multipart
    @POST("image")
    suspend fun postImage(
        @Part file: List<MultipartBody.Part>
    ): ImageResponse
}