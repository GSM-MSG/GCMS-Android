package com.msg.gcms.domain.usecase.user

import com.msg.gcms.data.remote.dto.datasource.user.request.UserSearchRequest
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.data.remote.network.ApiResult
import com.msg.gcms.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserUserCase @Inject constructor(
    private val userRepository : UserRepository
)  {
    // suspend fun getUserSearch(QueryString: UserSearchRequest): Flow<ApiResult<UserData>> {
    //     return flow {
    //         emit(ApiResult.loading())
    //         val result = userRepository.getUserSearch(QueryString)
    //         emit(result)
    //     }
    // }
}