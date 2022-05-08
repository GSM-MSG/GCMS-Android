package com.msg.gcms.data.remote.network

import com.msg.gcms.data.remote.dto.datasource.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.datasource.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.datasource.club.response.ClubInfoResponse
import com.msg.gcms.data.remote.dto.datasource.club.response.MemberInfo
import com.msg.gcms.data.remote.dto.datasource.club.response.SummaryClubResponse
import com.msg.gcms.data.remote.dto.datasource.club.response.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ClubAPI {

    @GET("club/list")
    suspend fun getClubList(
        @Query("type") type: String
    ): Response<List<SummaryClubResponse>>

    @GET("club/detail")
    suspend fun getDetail(
        @Query("type") type: String,
        @Query("q") clubName: String
    ): Response<ClubInfoResponse>

    // @POST("club/")
    // suspend fun postCreateClub(
    //     @Header("Authorization") authorization : String,
    //     @Body body : ClubInfoRequest
    // ): Response<Void>
    //
    // @PUT("club/")
    // suspend fun putChangeClub(
    //     @Header("Authorization") authorization: String,
    // ): Response<Void>

    @DELETE("club/")
    suspend fun deleteClub(
    ): Response<Void>

    @GET("club/members")
    suspend fun getMemberList(
        @Query("q") clubName: String,
        @Query("type") type: String
    ): Response<List<MemberInfo>>

    @GET("club/applicant")
    suspend fun getApplicantList(
        @Query("q") clubName: String,
        @Query("type") type: String
    ): Response<List<UserInfo>>

    @POST("club/accept")
    suspend fun postApplicantAccept(
        @Body body: MemberManagementRequest
    ): Response<Void>

    @POST("club/reject")
    suspend fun postApplicantReject(
        @Body body: MemberManagementRequest
    ): Response<Void>

    @PUT("club/open")
    suspend fun putClubOpen(
        @Body body: ClubIdentificationRequest
    ): Response<Void>

    @PUT("club/close")
    suspend fun putClubClose(
        @Body body: ClubIdentificationRequest
    ): Response<Void>

    @DELETE("club/kick")
    suspend fun deleteMemberExpel(
        @Body body: MemberManagementRequest
    ): Response<Void>

    @POST("club/apply")
    suspend fun postClubApply(
        @Body body: ClubIdentificationRequest
    ): Response<Void>

    @POST("club/cancel")
    suspend fun postClubCancel(
        @Body body: ClubIdentificationRequest
    ): Response<Void>

    @PUT("club/delegation")
    suspend fun putDelegationOfRepresentation(
        @Body body: MemberManagementRequest
    ): Response<Void>
}