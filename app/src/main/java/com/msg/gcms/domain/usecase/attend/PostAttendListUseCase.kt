package com.msg.gcms.domain.usecase.attend

import com.msg.gcms.domain.data.attend.PostAttendListRequestData
import com.msg.gcms.domain.repository.AttendRepository
import javax.inject.Inject

class PostAttendListUseCase @Inject constructor(
    private val attendRepository: AttendRepository
) {
    suspend operator fun invoke(body: PostAttendListRequestData) = runCatching {
        attendRepository.postAttendList(
            body = body
        )
    }
}