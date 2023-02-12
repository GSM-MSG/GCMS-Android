package com.msg.gcms.data.remote.datasource.image

import okhttp3.MultipartBody

interface ImageDataSource {
    suspend fun postImage(image : List<MultipartBody.Part>): List<String>
}