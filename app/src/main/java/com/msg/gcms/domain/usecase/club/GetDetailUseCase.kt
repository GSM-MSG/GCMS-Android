package com.msg.gcms.domain.usecase.club

import com.msg.gcms.data.repository.ClubRepositoryImpl
import javax.inject.Inject

class GetDetailUseCase @Inject constructor(
    private val repository : ClubRepositoryImpl
) {
    suspend fun getDetail(type: String, clubname: String) = repository.getDetail(type, clubname)
}