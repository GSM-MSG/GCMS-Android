package com.msg.gcms.domain.usecase.attend

import com.msg.gcms.domain.data.attend.PatchAttendStatusRequestData
import com.msg.gcms.domain.repository.AttendRepository
import javax.inject.Inject

class PatchAttendStatusUseCase @Inject constructor(
    private val attendRepository: AttendRepository
) {
    suspend operator fun invoke(body: PatchAttendStatusRequestData) = runCatching {
        attendRepository.patchAttendStatus(body = body)
    }
}