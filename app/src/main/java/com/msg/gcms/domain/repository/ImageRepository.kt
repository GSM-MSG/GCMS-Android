package com.msg.gcms.domain.repository

import okhttp3.MultipartBody
import retrofit2.Response

interface ImageRepository {
    suspend fun postImage(
        image: List<MultipartBody.Part>
    ): Response<List<String>>
}