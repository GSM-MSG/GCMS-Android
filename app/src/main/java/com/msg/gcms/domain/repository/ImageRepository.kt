package com.msg.gcms.domain.repository

import okhttp3.MultipartBody

interface ImageRepository {
    suspend fun postImage(
        image: List<MultipartBody.Part>
    ): List<String>
}