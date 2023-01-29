package com.msg.gcms.domain.datasource

import okhttp3.MultipartBody

interface ImageDataSource {
    suspend fun postImage(image : List<MultipartBody.Part>): List<String>
}