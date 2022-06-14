package com.msg.gcms.domain.usecase.user

import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import com.msg.gcms.domain.repository.UserRepository
import javax.inject.Inject

class UserUserCase @Inject constructor(
    private val userRepository : UserRepository
)  {
    suspend fun getSearchUser(userSearch : UserSearchRequest) = userRepository.getUserSearch(userSearch)
}