package com.msg.gcms.data.remote.datasource

import okhttp3.MultipartBody

interface ImageDataSource {
    suspend fun postImage(image : List<MultipartBody.Part>): List<String>
}