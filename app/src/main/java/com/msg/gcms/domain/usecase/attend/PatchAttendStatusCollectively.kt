package com.msg.gcms.domain.usecase.attend

import com.msg.gcms.domain.data.attend.PatchAttendStatusCollectivelyRequestData
import com.msg.gcms.domain.repository.AttendRepository
import javax.inject.Inject

class PatchAttendStatusCollectively @Inject constructor(
    private val attendRepository: AttendRepository
) {
    suspend operator fun invoke(body: PatchAttendStatusCollectivelyRequestData) = runCatching {
        attendRepository.patchAttendStatusCollectively(body = body)
    }
}