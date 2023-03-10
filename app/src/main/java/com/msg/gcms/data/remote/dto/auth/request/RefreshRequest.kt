package com.msg.gcms.data.remote.dto.auth.request

import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

data class RefreshRequest(
    @SerializedName("token")
    val token: String
)

fun RefreshRequest.toRequestBody() =
    this.toString().toRequestBody("application/json".toMediaTypeOrNull())