package com.msg.gcms.data.remote.network

import com.msg.gcms.data.remote.dto.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.club.request.CreateClubRequest
import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.request.ModifyClubInfoRequest
import com.msg.gcms.data.remote.dto.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.club.response.MemberInfo
import com.msg.gcms.data.remote.dto.club.response.SummaryClubResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ClubAPI {

    @GET("club/list")
    suspend fun getClubList(
        @Query("type") type: String
    ): List<SummaryClubResponse>

    @GET("club/detail")
    suspend fun getDetail(
        @Query("type") type: String,
        @Query("q") clubName: String
    ): ClubInfoResponse

    @POST("club/")
    suspend fun postCreateClub(
        @Body body: CreateClubRequest
    ): Void

    @PUT("club/")
    suspend fun putChangeClub(
        @Body body: ModifyClubInfoRequest
    ): Void

    @POST("club/delete")
    suspend fun deleteClub(
        @Body body: ClubIdentificationRequest
    ): Void

    @GET("club/members")
    suspend fun getMemberList(
        @Query("q") clubName: String,
        @Query("type") type: String
    ): MemberInfo

    @GET("club/applicant")
    suspend fun getApplicantList(
        @Query("q") clubName: String,
        @Query("type") type: String
    ): MemberInfo

    @POST("club/accept")
    suspend fun postApplicantAccept(
        @Body body: MemberManagementRequest
    ): Void

    @POST("club/reject")
    suspend fun postApplicantReject(
        @Body body: MemberManagementRequest
    ): Void

    @PUT("club/open")
    suspend fun putClubOpen(
        @Body body: ClubIdentificationRequest
    ): Void

    @PUT("club/close")
    suspend fun putClubClose(
        @Body body: ClubIdentificationRequest
    ): Void

    @POST("club/kick")
    suspend fun deleteMemberExpel(
        @Body body: MemberManagementRequest
    ): Void

    @POST("club/apply")
    suspend fun postClubApply(
        @Body body: ClubIdentificationRequest
    ): Void

    @POST("club/cancel")
    suspend fun postClubCancel(
        @Body body: ClubIdentificationRequest
    ): Void

    @PUT("club/delegation")
    suspend fun putDelegationOfRepresentation(
        @Body body: MemberManagementRequest
    ): Void
}