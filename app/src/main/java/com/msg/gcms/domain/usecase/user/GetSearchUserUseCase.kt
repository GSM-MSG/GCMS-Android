package com.msg.gcms.domain.usecase.user

import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class GetSearchUserUseCase @Inject constructor(
    private val userRepository : UserRepository
)  {
    suspend operator fun invoke(userSearch : Map<String,String>) = userRepository.getUserSearch(userSearch)
}