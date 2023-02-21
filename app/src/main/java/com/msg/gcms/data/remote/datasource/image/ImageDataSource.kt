package com.msg.gcms.data.remote.datasource.image

import com.msg.gcms.data.remote.dto.image.ImageResponse
import okhttp3.MultipartBody

interface ImageDataSource {
    suspend fun postImage(image : List<MultipartBody.Part>): ImageResponse
}