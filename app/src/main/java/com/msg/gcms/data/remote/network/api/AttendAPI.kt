package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.attend.request.PostAttendListRequest
import com.msg.gcms.data.remote.dto.attend.response.GetClubAttendListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.time.LocalTime

interface AttendAPI {

    @GET("attend/{club_id}")
    suspend fun getAttendList(
        @Path("club_id") clubId: Long,
        @Query("date") date: LocalDate,
        @Query("period") period: String
    ): GetClubAttendListResponse

    @POST("attend/{club_id}/club")
    suspend fun postAttendList(
        @Body body: PostAttendListRequest
    )
}