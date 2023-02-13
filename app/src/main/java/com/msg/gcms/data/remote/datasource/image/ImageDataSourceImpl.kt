package com.msg.gcms.data.remote.datasource.image

import com.msg.gcms.data.remote.network.api.ImageAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageDataSourceImpl @Inject constructor(
    val service: ImageAPI
) : ImageDataSource {
    override suspend fun postImage(image: List<MultipartBody.Part>): List<String> {
        return GCMSApiHandler<List<String>>()
            .httpRequest { service.postImage(file = image) }
            .sendRequest()
    }
}