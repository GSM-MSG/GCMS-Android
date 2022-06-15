package com.msg.gcms.data.usecase

import com.msg.gcms.data.repository.ClubRepositoryImpl
import com.msg.gcms.domain.repository.ClubRepository
import javax.inject.Inject

class GetDetailUseCase @Inject constructor(
    private val repository : ClubRepository
) {
    suspend fun getDetail(type: String, clubname: String) = repository.getDetail(type, clubname)
}