package com.msg.gcms.domain.usecase.attend

import com.msg.gcms.domain.repository.AttendRepository
import java.time.LocalDate
import javax.inject.Inject

class GetClubAttendListUseCase @Inject constructor(
    private val attendRepository: AttendRepository
) {
    suspend operator fun invoke(clubId: Long, date: LocalDate, period: String) = runCatching {
        attendRepository.getClubAttendList(
            clubId = clubId,
            date = date,
            period = period
        )
    }
}