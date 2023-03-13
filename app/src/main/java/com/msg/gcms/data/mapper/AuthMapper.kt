package com.msg.gcms.data.mapper

import com.msg.gcms.data.remote.dto.auth.response.SignInResponse
import com.msg.gcms.domain.data.auth.SignInResponseData

object AuthMapper {

    fun mapperToSignInData(data: SignInResponse): SignInResponseData{
        return SignInResponseData(
            accessToken = data.accessToken,
            refreshToken = data.refreshToken,
            accessExp = data.accessExp,
            refreshExp = data.refreshExp
        )
    }
}