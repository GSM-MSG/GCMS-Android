package com.msg.gcms.data.remote.dto.auth.response

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.auth.SignInResponseData

data class SignInResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("accessExp")
    val accessExp: String,
    @SerializedName("refreshExp")
    val refreshExp: String
)

fun SignInResponse.toSignInData(): SignInResponseData {
    return SignInResponseData(
        accessToken = accessToken,
        refreshToken = refreshToken,
        accessExp = accessExp,
        refreshExp = refreshExp
    )
}
