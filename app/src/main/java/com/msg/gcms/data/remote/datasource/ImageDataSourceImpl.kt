package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.network.ImageAPI
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class ImageDataSourceImpl @Inject constructor(
    val service: ImageAPI
): ImageDataSource  {
    override suspend fun postImage(image: List<MultipartBody.Part>): Response<List<String>> {
        return service.postImage(files = image)
    }
}