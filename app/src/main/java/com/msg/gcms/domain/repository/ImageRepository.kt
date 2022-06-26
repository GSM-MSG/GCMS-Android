package com.msg.gcms.domain.repository

import com.msg.gcms.data.remote.dto.datasource.Image.ImageResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface ImageRepository {
    suspend fun postImage(
        image: List<MultipartBody.Part>
    ): Response<ImageResponse>
}