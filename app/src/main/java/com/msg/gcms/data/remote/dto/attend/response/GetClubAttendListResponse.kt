package com.msg.gcms.data.remote.dto.attend.response

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.attend.GetClubAttendListResponseData
import com.msg.gcms.domain.data.attend.GetClubAttendListResponseData.User as DomainUser
import java.time.LocalDate
import java.util.UUID

data class GetClubAttendListResponse(
    @SerializedName("date")
    val date: LocalDate,
    @SerializedName("period")
    val period: String,
    @SerializedName("users")
    val users: List<User>
) {
    data class User(
        @SerializedName("id")
        val id: UUID,
        @SerializedName("attendanceId")
        val attendanceId: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("grade")
        val grade: Int,
        @SerializedName("classNum")
        val classNum: Int,
        @SerializedName("number")
        val number: Int,
        @SerializedName("attendanceState")
        val attendanceStatus: String
    )

    fun User.toDomainUser(): DomainUser {
        return DomainUser(
            id = id,
            attendanceId = attendanceId,
            name = name,
            grade = grade,
            classNum = classNum,
            number = number,
            attendanceStatus = attendanceStatus
        )
    }
}

fun GetClubAttendListResponse.toGetClubAttendListResponseData(): GetClubAttendListResponseData {
    return GetClubAttendListResponseData(
        date = date,
        period = period,
        users = users.map { it.toDomainUser() }
    )
}