package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.attend.response.GetClubAttendListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.time.LocalTime

interface AttendAPI {

    @GET("{club_id}")
    suspend fun getAttendList(
        @Path("club_id") clubId: Long,
        @Query("date") date: LocalDate,
        @Query("period") period: LocalTime
    ): GetClubAttendListResponse
}