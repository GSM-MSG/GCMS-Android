package com.msg.gcms.data.usecase

import com.msg.gcms.data.repository.ClubRepositoryImpl
import javax.inject.Inject

class GetDetailUseCase @Inject constructor(
    private val repository : ClubRepositoryImpl
) {
    suspend fun getDetail(type: String, clubname: String) = repository.getDetail(type, clubname)
}