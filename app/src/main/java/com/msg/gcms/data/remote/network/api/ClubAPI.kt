package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.club.create_club.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.get_club_detail.ClubDetailResponse
import com.msg.gcms.data.remote.dto.club.get_club_list.GetClubListResponse
import com.msg.gcms.data.remote.dto.club.modify_club_info.ModifyClubInfoRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ClubAPI {

    @GET("club")
    suspend fun getClubList(
        @Query("type") type: String
    ): List<GetClubListResponse>

    @GET("club/{club_id}")
    suspend fun getDetail(
        @Path("club_id") clubId: Long,
    ): ClubDetailResponse

    @POST("club")
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

    @PATCH("club/{club_id}/close")
    suspend fun putClubClose(
        @Path("club_id") clubId: Long
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