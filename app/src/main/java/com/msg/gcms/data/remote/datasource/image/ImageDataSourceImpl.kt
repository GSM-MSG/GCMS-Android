package com.msg.gcms.data.remote.datasource.image

import com.msg.gcms.data.remote.dto.image.ImageResponse
import com.msg.gcms.data.remote.network.api.ImageAPI
import com.msg.gcms.data.remote.util.GCMSApiHandler
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageDataSourceImpl @Inject constructor(
    val service: ImageAPI
) : ImageDataSource {
    override suspend fun postImage(image: List<MultipartBody.Part>): ImageResponse {
        return GCMSApiHandler<ImageResponse>()
            .httpRequest { service.postImage(file = image) }
            .sendRequest()
    }
}