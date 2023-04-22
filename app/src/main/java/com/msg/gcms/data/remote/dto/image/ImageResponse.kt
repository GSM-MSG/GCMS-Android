package com.msg.gcms.data.remote.dto.image

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.image.ImageData

data class ImageResponse(
    @SerializedName("images")
    val images: List<String>
)

fun ImageResponse.toImageData(): ImageData {
    return ImageData(images = images)
}