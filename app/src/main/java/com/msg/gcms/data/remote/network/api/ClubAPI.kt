package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.club.response.SummaryClubResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ClubAPI {

    @GET("club/")
    suspend fun getClubList(
        @Query("type") type: String
    ): List<SummaryClubResponse>

    @GET("club/{club_id}")
    suspend fun getDetail(
        @Path("clubId") clubId: Long,
    ): ClubInfoResponse

    @POST("club/")
    suspend fun postCreateClub(
        @Body body: CreateClubRequest
    )

    @PATCH("club/{club_id}")
    suspend fun putChangeClub(
        @Path("club_id") clubId: Long,
        @Body body: ModifyClubInfoRequest
    )

    @PATCH("club/{club_id}/open")
    suspend fun putClubOpen(
        @Path("club_id") clubId: Long,
    )

    @PUT("club/close")
    suspend fun putClubClose(
        @Body body: ClubIdentificationRequest
    )

    @DELETE("club/{club_id}/exit")
    suspend fun exitClub(
        @Path("club_id") clubId: Long
    )

    @DELETE("club/{club_id}")
    suspend fun deleteClub(
        @Path("club_id") clubId: Long
    )

}