package com.msg.gcms.data.remote.network

import com.msg.gcms.data.remote.dto.datasource.Image.ImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageAPI {
    @Multipart
    @POST("image")
    suspend fun postImage(
        @Part image : List<MultipartBody.Part>
    ): Response<ImageResponse>
}