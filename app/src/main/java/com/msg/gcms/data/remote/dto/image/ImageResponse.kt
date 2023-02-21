package com.msg.gcms.data.remote.dto.image

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("images")
    val images: List<String>
)