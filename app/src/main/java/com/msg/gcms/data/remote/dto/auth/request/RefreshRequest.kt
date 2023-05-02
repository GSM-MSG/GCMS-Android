package com.msg.gcms.data.remote.dto.auth.request

import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

data class RefreshRequest(
    @SerializedName("token")
    val token: String
)

fun RefreshRequest.toRequestBody(): RequestBody {
    return JSONObject().apply {
        put("token", this@toRequestBody.token)
    }.toString().toRequestBody("application/json".toMediaTypeOrNull())
}