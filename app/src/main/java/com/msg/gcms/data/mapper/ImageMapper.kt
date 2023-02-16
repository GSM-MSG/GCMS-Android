package com.msg.gcms.data.mapper

import com.msg.gcms.data.remote.dto.image.ImageResponse
import com.msg.gcms.domain.data.image.ImageData

object ImageMapper {

    fun mapperToImageData(data: ImageResponse): ImageData {
        return ImageData(images = data.images)
    }
}