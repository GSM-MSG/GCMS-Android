package com.msg.gcms.data.remote.datasource

import okhttp3.MultipartBody
import retrofit2.Response

interface ImageDataSource {
    suspend fun postImage(image : List<MultipartBody.Part>) : Response<List<String>>
}