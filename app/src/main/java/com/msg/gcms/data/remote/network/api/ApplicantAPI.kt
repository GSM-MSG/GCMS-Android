package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.club.request.ClubIdentificationRequest
import com.msg.gcms.data.remote.dto.club.request.MemberManagementRequest
import com.msg.gcms.data.remote.dto.club.response.MemberInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApplicantAPI {

    @GET("club/applicant")
    suspend fun getApplicantList(
        @Query("q") clubName: String,
        @Query("type") type: String
    ): MemberInfo

    @POST("club/apply")
    suspend fun postClubApply(
        @Body body: ClubIdentificationRequest
    )

    @POST("club/accept")
    suspend fun postApplicantAccept(
        @Body body: MemberManagementRequest
    )

    @POST("club/reject")
    suspend fun postApplicantReject(
        @Body body: MemberManagementRequest
    )
}