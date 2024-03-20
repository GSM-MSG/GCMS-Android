package com.msg.gcms.data.remote.dto.attend.response

import com.google.gson.annotations.SerializedName
import com.msg.gcms.domain.data.attend.GetClubAttendListResponseData
import com.msg.gcms.domain.data.attend.GetClubAttendListResponseData.User as DomainUser
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class GetClubAttendListResponse(
    @SerializedName("date")
    val date: LocalDate,
    @SerializedName("period")
    val period: LocalTime,
    @SerializedName("users")
    val users: List<User>
) {
    data class User(
        @SerializedName("id")
        val id: UUID,
        @SerializedName("name")
        val name: String,
        @SerializedName("grade")
        val grade: Int,
        @SerializedName("classNum")
        val classNum: Int,
        @SerializedName("number")
        val number: Int,
        @SerializedName("attendanceState")
        val attendanceState: String
    )

    fun User.toDomainUser(): DomainUser {
        return DomainUser(
            id = id,
            name = name,
            grade = grade,
            classNum = classNum,
            number = number,
            attendanceState = attendanceState
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