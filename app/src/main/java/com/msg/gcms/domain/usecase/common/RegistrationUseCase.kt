package com.msg.gcms.domain.usecase.common

import com.msg.gcms.data.remote.dto.datasource.auth.request.CodeIssuanceRequest
import com.msg.gcms.data.remote.dto.datasource.auth.request.QueryString
import com.msg.gcms.data.remote.dto.datasource.auth.request.RegisterRequest
import com.msg.gcms.domain.repository.CommonRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val repository: CommonRepository
) {
    suspend fun postRegistration(request: RegisterRequest) = repository.postRegistration(request)

    suspend fun postEmail(body : CodeIssuanceRequest) = repository.postEmail(body)

    suspend fun headCheckCode (queryString: QueryString) = repository.headCheckCode(queryString)
}