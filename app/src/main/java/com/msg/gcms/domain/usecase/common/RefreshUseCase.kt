package com.msg.gcms.domain.usecase.common

import com.msg.gcms.domain.repository.CommonRepository
import javax.inject.Inject

class RefreshUseCase @Inject constructor(
    private val repository: CommonRepository
) {
    suspend fun postRefresh() = repository.postRefresh()
}