package com.msg.gcms.domain.usecase.user

import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository : UserRepository
)  {
    suspend fun getSearchUser(userSearch : Map<String,String>) = userRepository.getUserSearch(userSearch)
}