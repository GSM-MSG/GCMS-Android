package com.msg.gcms.domain.repository

import com.msg.gcms.domain.data.image.ImageData
import okhttp3.MultipartBody

interface ImageRepository {
    suspend fun postImage(
        image: List<MultipartBody.Part>
    ): ImageData
}