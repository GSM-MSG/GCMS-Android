package com.msg.gcms.data.remote.dto.datasource.base

import com.google.gson.annotations.SerializedName

data class BaseRequest(
    @SerializedName("code")
    val statusCode: Int,
    @SerializedName("message")
    val errorMsg: String
)
