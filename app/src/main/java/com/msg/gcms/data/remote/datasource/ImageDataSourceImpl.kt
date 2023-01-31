package com.msg.gcms.data.remote.datasource

import com.msg.gcms.data.remote.network.ImageAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import com.msg.gcms.domain.datasource.ImageDataSource
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageDataSourceImpl @Inject constructor(
    val service: ImageAPI
) : ImageDataSource {
    override suspend fun postImage(image: List<MultipartBody.Part>): List<String> {
        return GCMSApiHandler<List<String>>()
            .httpRequest { service.postImage(files = image) }
            .sendRequest()
    }
}