package com.msg.gcms.data.remote.dto.base

import com.google.gson.annotations.SerializedName

data class BaseRequest(
    @SerializedName("status")
    val statusCode: Int,
    @SerializedName("message")
    val errorMsg: String
)
