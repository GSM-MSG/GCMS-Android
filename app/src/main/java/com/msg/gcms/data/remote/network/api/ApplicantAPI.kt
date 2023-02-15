package com.msg.gcms.data.remote.network.api

import com.msg.gcms.data.remote.dto.applicant.get_applicant_list.GetApplicantListResponse
import com.msg.gcms.data.remote.dto.applicant.club_apply_accept.ClubApplyAcceptRequest
import com.msg.gcms.data.remote.dto.applicant.club_apply_reject.ClubApplyRejectRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApplicantAPI {

    @GET("applicant/{club_id}")
    suspend fun getApplicantList(
        @Path("club_id") clubId: Long
    ): GetApplicantListResponse

    @POST("applicant/club_id")
    suspend fun postClubApply(
        @Path("club_id") clubId: Long
    )

    @DELETE("applicant/{club_id}")
    suspend fun deleteClubApply(
        @Path("club_id") clubId: Long
    )

    @POST("applicant/{club_id}/accept")
    suspend fun postApplicantAccept(
        @Path("club_id") clubId: Long,
        @Body body: ClubApplyAcceptRequest
    )

    @POST("applicant/{club_id}/reject")
    suspend fun postApplicantReject(
        @Path("club_id") clubId: Long,
        @Body body: ClubApplyRejectRequest
    )
}